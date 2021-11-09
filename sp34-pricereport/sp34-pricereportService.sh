name=sp34-pricereport
echo "开始sp34-pricereport服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp34-pricereport"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --myupload.serverIMGUpload=/www >./sp34-pricereportlog.out 2>&1 &
tail -f ./sp34-pricereportlog.out
echo "已启动sp34-pricereport服务..."
