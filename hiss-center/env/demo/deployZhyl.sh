unzip dist.zip
rm -r project-zhyl-admin-vue3-java
mv dist project-zhyl-admin-vue3-java
chmod -R 775 *
rm -rf dist.zip
docker restart hiss-nginx
