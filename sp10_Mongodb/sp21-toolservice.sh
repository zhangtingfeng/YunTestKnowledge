name=sp21-tools
echo "开始sp21-tools服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp21-tools"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --myupload.serverIMGUpload=/www/StaticImg/edu >./sp21-toolslog.out 2>&1 &
tail -f ./sp21-toolslog.out
echo "已启动sp21-tools服务...sp21-toolslog.out"
