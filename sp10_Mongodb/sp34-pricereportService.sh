name=sp10-Mongodb
echo "开始sp10-Mongodb服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp10-Mongodb"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --myupload.serverIMGUpload=/www >./sp10-Mongodblog.out 2>&1 &
tail -f ./sp10-Mongodblog.out
echo "已启动sp10-Mongodb服务..."
