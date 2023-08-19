package cn.itcast.hiss.process.activiti.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.itcast.hiss.process.activiti.dto.scaffold.ScaffoldDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.nio.file.StandardCopyOption;
import java.util.*;

/*
 * @author miukoo
 * @description 若依导出工具
 * @date 2023/7/13 16:20
 * @version 1.0
 **/
public class RuoyiUtil {

    /**
     * 下载并下载
     * @param dto
     * @param root
     */
    private static File downloadAndUnzip(ScaffoldDto dto, File root){
        File zip = new File(root,dto.getName()+"-"+dto.getVersion()+".zip");
        if(!zip.exists()) {
            HttpUtil.downloadFile(String.format("https://gitee.com/y_project/RuoYi-Vue/repository/archive/%s", dto.getVersion()), root);
        }
        File arm = new File(root,dto.getName()+"-"+dto.getVersion());
        if(arm.exists()){
            FileUtil.del(arm);
        }
        if("ruoyi".equals(dto.getTemplateName())){
            ZipUtil.unzip(zip,root);
        }else{
            ZipUtil.unzip(zip,arm);
        }
        return arm;
    }

    public static void parseModule(String module,File src, File arm,Map<String,String> parms,StringBuilder desc,Map<String,JSONObject> menus){
        File common = new File(src,module);
        if(common.exists()){
            File json = new File(common,"hiss.json");
            Map<String, List<JSONObject>> fileConfig = new HashMap<>();
            JSONArray array = null;
            if(json.exists()){
                String jsonContent = FileUtil.readUtf8String(json);
                array = JSON.parseArray(jsonContent);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String file = jsonObject.getString("file");
                    if(file!=null){
                        List<JSONObject> list = fileConfig.getOrDefault(file, new ArrayList<>());
                        list.add(jsonObject);
                        fileConfig.put(file,list);
                    }
                }
            }
            copyFile(common,common,arm,fileConfig,parms,desc,menus);
            execFileConfig(arm,array,parms,desc,menus);
        }
    }

    /**
     * 执行
     * @param arm
     * @param jsonConfig
     * @param parms
     * @param desc
     */
    private static void execFileConfig(File arm, JSONArray jsonConfig, Map<String, String> parms, StringBuilder desc,Map<String,JSONObject> menus) {
        if(jsonConfig!=null){
            for (int i = 0; i < jsonConfig.size(); i++) {
                JSONObject jsonObject = jsonConfig.getJSONObject(i);
                if(jsonObject.getString("_")==null){
                    String file = jsonObject.getString("file");
                    execJSONObject(jsonObject,null,file,arm,parms,desc,menus);
                }
            }
        }
    }

    public static void copyFile(File rootSrc, File src, File arm,Map<String, List<JSONObject>> fileConfig,Map<String,String> parms,StringBuilder desc,Map<String,JSONObject> menus){
        File[] files = src.listFiles();
        if(files!=null){
            for (File file : files) {
                if(file.isFile()){
                    String name = file.getName();
                    if(name.equals("hiss.json")){
                        continue;
                    }
                    String path = file.getPath().replace(rootSrc.getPath(),"").replace("\\","/");
                    List<JSONObject> jsonObjects = fileConfig.get(path);
                    if(jsonObjects!=null){
//                        System.out.println("============"+path);
                        for (JSONObject jsonObject : jsonObjects) {
                            execJSONObject(jsonObject,file,path,arm,parms,desc,menus);
                        }
                    }else{
                        FileUtil.copyFile(file,new File(arm,path), StandardCopyOption.REPLACE_EXISTING);
                    }
                }else{
                    copyFile(rootSrc,file,arm,fileConfig,parms,desc,menus);
                }
            }
        }
    }

    /**
     *
     * @param jsonObject
     * @param file
     * @param path
     * @param arm
     * @param parms
     * @param desc
     */
    private static void execJSONObject(JSONObject jsonObject,File file,String path,File arm,Map<String,String> parms,StringBuilder desc,Map<String,JSONObject> menus){
        String type = jsonObject.getString("type");
        if("appendAndReplace".equals(type)){
            jsonObject.put("_","ok");
            appendAndReplace(file,path,jsonObject,arm,parms,desc);
        }
        if("replaceFile".equals(type)){
            jsonObject.put("_","ok");
            replaceFile(path,jsonObject,arm,parms,desc);
        }
        if("replaceParams".equals(type)){
            jsonObject.put("_","ok");
            replaceParams(path,jsonObject,arm,parms,desc);
        }
        if("menu".equals(type)){
            jsonObject.put("_","ok");
            parseMenu(path,jsonObject,arm,parms,desc,menus);
        }
    }

    private static void replaceParams(String path, JSONObject jsonObject, File arm, Map<String, String> parms, StringBuilder desc) {
        File out = new File(arm,path);
        if(out.exists()){
            String cot = FileUtil.readUtf8String(out);
            cot = replaceConent(cot,parms);
            FileUtil.writeUtf8String(cot,out);
        }
    }

    /**
     * 解析菜单
     * @param path
     * @param jsonObject
     * @param arm
     * @param parms
     * @param desc
     */
    private static void parseMenu(String path, JSONObject jsonObject, File arm, Map<String, String> parms, StringBuilder desc,Map<String,JSONObject> menus) {
        JSONObject menu = jsonObject.getJSONObject("menu");
        // 替换菜单数据中的模板数据
        String temp = JSON.toJSONString(menu);
        temp = replaceConent(temp,parms);
        menu = JSON.parseObject(temp);
        if(menu!=null){
            String menuPath = menu.getString("path");
            JSONObject mapMenu = menus.get(menuPath);
            if(mapMenu==null){
                mapMenu = menu;
            }else {
                JSONArray mapChildren = mapMenu.getJSONArray("children");
                JSONArray children = menu.getJSONArray("children");
                if (mapChildren != null && children != null) {
                    mapChildren.addAll(children);
                } else if (children != null) {
                    mapChildren = children;
                }
                mapMenu.put("children",mapChildren);
            }
            menus.put(menuPath,mapMenu);
        }
    }

    /**
     * 把一个文件内容添加到对应文件后面
     * @param path
     * @param jsonObject
     * @param arm
     * @param parms
     */
    private static void replaceFile(String path,JSONObject jsonObject,File arm,Map<String,String> parms,StringBuilder desc){
        File out = new File(arm,path);
        String src = jsonObject.getString("src");
        String rep = jsonObject.getString("rep");
        rep = replaceConent(rep,parms);
        if(out.exists()&&StrUtil.isNotEmpty(src)&&StrUtil.isNotEmpty(rep)){
            String cot = FileUtil.readUtf8String(out);
            cot = cot.replace(src,rep);
            FileUtil.writeUtf8String(cot,out);
            desc.append(String.format("## 替换文件内容[已完成]\n > 把%s文件中的内容进行下面替换。\n替换前的字符串：\n```\n%s\n```\n替换后的字符串\n```\n%s\n```\n\n",path,src,rep));
        }else{
            desc.append(String.format("## 替换文件内容[待完成]\n > 把%s文件中的内容进行下面替换。\n替换前的字符串：\n```\n%s\n```\n替换后的字符串\n```\n%s\n```\n\n",path,src,rep));
        }
    }

    /**
     * 把一个文件内容添加到对应文件后面
     * @param file
     * @param path
     * @param jsonObject
     * @param arm
     * @param parms
     */
    private static void appendAndReplace(File file,String path,JSONObject jsonObject,File arm,Map<String,String> parms,StringBuilder desc){
        String cot = FileUtil.readUtf8String(file);
        cot = replaceConent(cot,parms);
        if(StrUtil.isNotEmpty(cot)){
            File out = new File(arm,path);
            if(out.exists()) {
                FileUtil.appendUtf8String(cot, out);
                desc.append(String.format("## 配置客户端连接[已完成]\n > 把%s文件中的内容，添加到%s末尾。\n\n", file.getName(), path));
            }else{
                desc.append(String.format("## 配置客户端连接[待完成]\n > 把下面内容，添加到%s末尾。\n```yml\n%s\n```\n\n\n", path,cot));
            }
        }
    }

    /**
     * 替换变量参数
     * @param content
     * @param parms
     */
    private static String replaceConent(String content,Map<String,String> parms){
        for (String key : parms.keySet()) {
            content = content.replace(key,parms.get(key));
        }
        return content;
    }

    /**
     * 导出若依
     * @param dto
     * @param src
     * @param arm
     */
    public static File exportRuoyi(ScaffoldDto dto, File src, File arm){
//        src = new File(src,"RuoYi-Vue-"+dto.getVersion());
        src = new File(src,dto.getName());
        StringBuilder desc = new StringBuilder();
        // 准备全量文件
        if("2".equals(dto.getType())) {
            arm = downloadAndUnzip(dto, arm);
        }else{
            desc.append(String.format("## 增量文件使用说明\n > - 1、把当前文件夹夹下的所有文件直接覆盖你工程的文件\n > - 2、参考下述配置操作\n\n"));
            arm = new File(arm,dto.getName()+"-"+dto.getVersion());
            if(arm.exists()){
                FileUtil.del(arm);
            }
        }
        // 开始合并文件
        Map<String,String> parms = new HashMap<>();
        parms.put("#hiss{app}",dto.getAppId());
        parms.put("#hiss{host}",dto.getHost());
        parms.put("#hiss{httpPort}",dto.getHttpPort());
        parms.put("#hiss{tcpPort}",dto.getTcpPort());
        parms.put("#hiss{version}","1.0-SNAPSHOT");
        Map<String,JSONObject> menus = new HashMap<>();
        parseModule("common",src,arm,parms,desc,menus);
        if(dto.getContents()!=null){
            for (Map content : dto.getContents()) {
                String type = (String) content.get("type");
                if("module".equals(type)){
                    parseModule((String) content.get("id"),src,arm,parms,desc,menus);
                }
                if("form".equals(type)){
                    parms.put("#hiss{dataId}", (String) content.get("id"));
                    parms.put("#hiss{dataName}", (String) content.get("name"));
                    parms.put("#hiss{groupId}", ((String) content.get("groupId")).replace("_","-"));
                    parms.put("#hiss{groupName}", (String) content.get("groupName"));
                    parseModule("data",src,arm,parms,desc,menus);
                }
            }
        }

        // 处理菜单
        if(!"1".equals(dto.getSql())){
            // 生成动态菜单的SQL语句
            String sql = "";
            if(dto.getTemplateName().equals("ruoyi")){
                sql = genRuoyiSql(menus);
            }
            desc.append(String.format("## 添加路由配置[待完成]\n > 把下面的SQL数据插入到sys_menu表中：\n```sql\n%s\n```\n",sql));
        }else{
            StringJoiner temp = new StringJoiner(",\n");
            for (JSONObject value : menus.values()) {
                temp.add(JSONUtil.formatJsonStr(JSON.toJSONString(value)));
            }
            String json = temp.toString().replace("\"^","").replace("^\"","");
            desc.append(String.format("## 添加路由配置[待完成]\n > 把下面的路由数据拷贝到src/router/index.js文件中：\n```json\n%s\n```\n",json));
        }
        // 在跟路径写入hiss.md
        File md = new File(arm,"hiss.md");
        FileUtil.writeUtf8String(desc.toString(),md);
        // 压缩目录
        File zip = new File(arm.getParentFile(),"temp.zip");
        return ZipUtil.zip(arm.getPath(), zip.getPath());
    }

    /**
     * 生成SQL语句
     * @param menus
     * @return
     */
    private static String genRuoyiSql(Map<String,JSONObject> menus){
        StringJoiner joiner = new StringJoiner(";\n");
        long id = 6666;
        for (String key : menus.keySet()) {
            JSONObject jsonObject = menus.get(key);
            JSONObject meta = jsonObject.getJSONObject("meta");
            JSONArray children = jsonObject.getJSONArray("children");
            String sql = String.format("INSERT INTO `sys_menu`(`menu_id`,`visible`,`create_time`,`menu_type`,`query`,`is_cache`,`icon`,`remark`,`is_frame`,`create_by`,`path`,`parent_id`,`menu_name`,`perms`,`order_num`,`update_by`,`status`) VALUES (%d,'0','2023-07-12T17:55','M','',0,'%s','',1,'admin','%s',%s,'%s','',1,'','0')",
                    id,
                    meta.getString("icon"),
                    jsonObject.getString("path").replace("/",""),
                    0,
                    meta.getString("title"));
            joiner.add(sql);
            if(children!=null){
                long parentId = id;
                long subId = id*1000;
                for (int i = 0; i < children.size(); i++) {
                    JSONObject temp = children.getJSONObject(i);
                    JSONObject tempMeta = temp.getJSONObject("meta");
                    String tempSql = String.format("INSERT INTO `hiss-ruoyi`.`sys_menu`(`menu_id`,`visible`,`create_time`,`menu_type`,`query`,`is_cache`,`icon`,`remark`,`is_frame`,`create_by`,`path`,`component`,`parent_id`,`menu_name`,`perms`,`order_num`,`update_by`,`status`) VALUES (%s,%s,'2023-07-12T17:55','C','%s',0,'%s','',1,'admin','%s','%s',%s,'%s','hiss:%s:list',1,'','0')",
                            subId++,
                            temp.getBoolean("hidden")==null?"0":(temp.getBoolean("hidden")?"1":"0"),
                            tempMeta.containsKey("params")?JSON.toJSONString(tempMeta.getJSONObject("params")):"",
                            tempMeta.getString("icon"),
                            temp.getString("path").replace("/",""),
                            temp.getString("component").replace("() => import('@/views/","").replace("')","").replace("^",""),
                            parentId,
                            tempMeta.getString("title"),
                            temp.getString("path").replace("/","")
                    );
                    joiner.add(tempSql);
                }
            }
            id++;
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        File src= new File("C:\\Users\\lenovo\\Downloads\\RuoYi-Vue-v3.8.6");
        File arm = new File("E:\\111111\\ruoyi");
//        Map<String,String> parms = new HashMap<>();
//        parms.put("#hiss{app}","zhyl-app");
//        parms.put("#hiss{host}","172.16.43.97");
//        parms.put("#hiss{version}","1.0-SNAPSHOT");
//        StringBuilder desc = new StringBuilder();
//        Map<String,JSONObject> menus = new HashMap<>();
//        parseModule("oa",src,arm,parms,desc,menus);
//        parseModule("dev",src,arm,parms,desc,menus);
//        parseModule("bis",src,arm,parms,desc,menus);
//        parseModule("form",src,arm,parms,desc,menus);
//        parseModule("instance",src,arm,parms,desc,menus);
//        System.out.println(JSON.toJSONString(menus));

        ScaffoldDto dto = new ScaffoldDto();
        dto.setVersion("v3.8.6");
        dto.setSql("1");
        dto.setType("1");
        exportRuoyi(dto,src,arm);
    }

}
