name=sp25-student
echo "开始sp25-student服务"
kill -9 `ps -ef | grep ${name}service.jar |grep -v grep| awk '{print $2}'`
echo "关闭服务结束..."

