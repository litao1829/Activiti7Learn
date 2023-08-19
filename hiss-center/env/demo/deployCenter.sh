unzip dist.zip
rm -r hiss-center
mv dist hiss-center
chmod -R 775 *
rm -rf dist.zip
docker restart hiss-nginx
