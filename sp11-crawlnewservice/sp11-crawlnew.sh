name=sp11-crawlnew
echo "开始sp11-crawlnewservice服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."
echo "sp11-crawlnewservice"
nohup java -Dloader.path=libs/ -jar ./${name}service.jar --myupload.serverIMGUpload=/www --myupload.img_url=StaticImg/edu/News>./logsp11-crawlnewservice.out 2>&1 &
tail -f ./logspsp11-crawlnewservice.out
echo "已启动sp11-crawlnewservice服务..."
