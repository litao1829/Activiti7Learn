unzip dist.zip
rm -r ppt
mv dist ppt
chmod -R 775 *
rm -rf dist.zip
docker restart hiss-nginx
