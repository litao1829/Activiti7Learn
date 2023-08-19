package cn.itcast.hiss.process.activiti.configurator;

import cn.hutool.core.collection.CollectionUtil;
import cn.itcast.hiss.process.activiti.behavior.HissDefaultActivityBehaviorFactory;
import cn.itcast.hiss.process.activiti.configurator.function.StrUtilFunctionProvider;
import cn.itcast.hiss.process.activiti.configurator.function.VariableFunctionProvider;
import cn.itcast.hiss.process.activiti.configurator.validator.HissSequenceflowValidator;
import cn.itcast.hiss.process.activiti.listener.GlobalEventListener;
import cn.itcast.hiss.process.activiti.listener.HissDefaultListenerFactory;
import lombok.extern.slf4j.Slf4j;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.cfg.AbstractProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.validation.validator.ValidatorSet;
import org.activiti.validation.validator.impl.SequenceflowValidator;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/*
 * @author miukoo
 * @description 实现自定义扩展配置
 * @date 2023/5/26 9:23
 * @version 1.0
 **/
@Slf4j
public class HissProcessEngineConfigurator extends AbstractProcessEngineConfigurator {

    @Override
    public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (processEngineConfiguration instanceof SpringProcessEngineConfiguration) {
            SpringProcessEngineConfiguration processEngine = (SpringProcessEngineConfiguration) processEngineConfiguration;
            ApplicationContext applicationContext = processEngine.getApplicationContext();
            HissDefaultListenerFactory hissDefaultListenerFactory = applicationContext.getBean(HissDefaultListenerFactory.class);
            HissDefaultActivityBehaviorFactory hissDefaultActivityBehaviorFactory = applicationContext.getBean(HissDefaultActivityBehaviorFactory.class);
            processEngine.setListenerFactory(hissDefaultListenerFactory);
            processEngine.setActivityBehaviorFactory(hissDefaultActivityBehaviorFactory);
            processEngine.setEventListeners(CollectionUtil.newArrayList(applicationContext.getBean(GlobalEventListener.class)));
            processEngine.setHistoryLevel(HistoryLevel.FULL);
            log.info("============HissProcessEngineConfigurator=====配置事件、行文工厂完成==============");
        }
    }

    private void addFunction(ProcessEngineConfigurationImpl processEngineConfiguration) {
        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();
        if (expressionManager != null) {
            List<CustomFunctionProvider> customFunctionProviders = expressionManager.getCustomFunctionProviders();
            if (customFunctionProviders == null) {
                customFunctionProviders = new ArrayList<>();
            }
            customFunctionProviders.add(new StrUtilFunctionProvider());
            customFunctionProviders.add(new VariableFunctionProvider());
            expressionManager.setCustomFunctionProviders(customFunctionProviders);
        }
    }

    @Override
    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        addFunction(processEngineConfiguration);
        List<ValidatorSet> validatorSets = processEngineConfiguration.getProcessValidator().getValidatorSets();
        for (ValidatorSet validatorSet : validatorSets) {
            validatorSet.removeValidator(SequenceflowValidator.class);
            validatorSet.addValidator(new HissSequenceflowValidator());
        }
        log.info("============自定义Activiti事件创建工厂：==============" + processEngineConfiguration.getListenerFactory());
        log.info("============自定义Activiti行文创建工厂：==============" + processEngineConfiguration.getActivityBehaviorFactory());
        log.info("============初始化向客户端分发全局事件类：==============" + processEngineConfiguration.getEventListeners());
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

}
