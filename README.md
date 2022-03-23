# java实用工具类
 推荐首先使用Hutool,可以解决大部分问题。
<p align="left">
	👉 <a href="https://hutool.cn">https://hutool.cn/</a> 👈
</p>

Hutool是一个小而全的Java工具类库，通过静态方法封装，降低相关API的学习成本，提高工作效率，使Java拥有函数式语言般的优雅，让Java语言也可以“甜甜的”。
## 📚当前版本库简介
    网上形形色色的工具，代码文档质量参差不齐，在浪里淘沙的过程中，分秒必争的摸鱼时间被白白浪费，不免令人唏嘘。
    倒不如工作过程中，自己动手，风衣足食，整理下工作中经过验证的好用的工具类，写好文档，以便在之后的工作中，信手捏来。
## 一、sftp工具类
   ### 1、工具类使用的的maven依赖为:
   
            <!-- https://mvnrepository.com/artifact/com.jcraft/jsch -->
            <dependency>
                <groupId>com.jcraft</groupId>
                <artifactId>jsch</artifactId>
                <version>0.1.55</version>
            </dependency>
   ### 2、使用
           //配置信息
           String host = "127.0.0.1";
           String username = "admin";
           String password = "123456";
           int point = 22;
   
           //配置sftp客户端
           ChannelSftp sftp = SftpUtil.connectSFTP(host,username,password,point);
           FTPClient ftpClient = FtpUtil.connect(host,username,password);
           String directory = "/";//sftp文件所在目录
           String fileName = "test.txt";//文件名称
           String saveFile ="D:\\1work\\文档\\test1.txt";//保存的文件路径或者是重新命名的文件
           //下载文件
           SftpUtil.downloadFile(directory,fileName,saveFile,sftp);
           SftpUtil.disconnected(sftp);
           //读取文件流
           InputStream inputStream =FtpUtil.readFile(directory,fileName,ftpClient);
           BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
           String s = null;
           while ((s=reader.readLine())!=null){
               System.out.println(s);
           }
