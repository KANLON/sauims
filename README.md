# SAU-IMS
大创项目：校社联管理系统<br/>

<br/>
该仓库是小组仓库的备份，是基于idea软件提交的仓库。


工作进度:
<h4>2018/8/22</h4>
0.所有合并在master分支上了，第一个版本。<br/>
1.修改了邮箱修改的问题，改为修改邮箱时，向新邮箱发送验证码<br/>
2.项目后端功能已经全部完成，经基本测试，没有问题。还需要交叉测试和结合前端测试。<br/>
3.前端代码已经提交到front-end分支中去了。<br/>
4.项目地址：http://kanlon.ink  测试账号和密码：https://documenter.getpostman.com/view/3892535/RWM8UC2C 该地址中的首页——》登陆 备注中有。<br/>

<h4>2018/5/10</h4>
1.整合了前后端，布置到服务器上去了，基本不过还是有很多问题。<br/>
2.问题1，不能两个不同用户登录。<br/>
3.基本上注册，发送消息，删除功能都还不可以实现，怀疑是前端没有按照文档要求来写<br/>

<h4>2018/5/6</h4>
1.添加了社团端，下载年度注册文件的功能。<br/>
2.将校社联的注册审核链接那里将/role  /auditMsgId调换了位置，即新的链接变成了/sau/audit/join/｛roleId｝/｛auditMsgId｝，这种形式<br/>

<h4>2018/5/2</h4>
1.在社团详细信息中加了是否点赞字段（这个是没有什么用的）。<br/>
2.在用户点赞那里的返回数据的data中加了喜欢人数，就是类似：data:12。<br/>

<h4>2018/4/24</h4>
1.初步整合前端，首页展示和验证码，登录，还有点进某个社团的详细信息应该没问题<br/>
2.在首页展示社团的时候，添加了给各个年级数以及男女数,预计下一步会给有展示社团详细信息的加上这些。<br/>
3.前后端又分开来写了，因为发现前端太多错误，不能在idea中写，决定把前端分离到tomcat上写，自己用文件模拟json，大大加快了修改进度。<br/>


<h4>2018/4/23</h4>
1.初步整合前端，还存在很多问题，暂时首页展示，链接没问题，验证码和某个社团的详情都需要调一下<br/>

<h4>2018/4/23</h4>
1.从社团描述中分离出口号，口号以“。”来与社团描述分割开。<br/>
2.更新了数据库，将所有的社团展示图和校社联展示图改为默认的图。<br/>
3.增加了前端文件，暂时整合了，还存在很大问题。<br/>
4.增加了管理端的开放权限。<br/>

<h4>2018/4/22</h4>
1.将个人头像改为输出流输出，社团和校社联头像还是经过图片资源输出。<br/>
2.修改了一些小bug，如头像存储问题，社团中心信息增加了男女数和各个年级数，个人信息添加了手机号。<br/>
3.更新了数据库文件<br/>

<h4>2018/4/10</h4>
1、所有功能已经完成，按照接口的数据形式进行发送和请求即可。<br/>
2、还差offset，limit这两个参数需要修改内容和查询出来的数据需要按照时间排序。<br/>
3、计划把接口文档弄规范化，把接口功能和解释统一格式说明。<br/>

<h4>2018/2/16</h4>
1、继续完善服务层接口，预计完成时间为5天<br/>
2、各个发送功能测试正常，redis服务器暂时正常<br/>
3、修复若干个bug<br/>
4、未来加入投票系统，定时任务功能支持<br/>
5、预计一个星期后开始编写点赞功能(所有事先设计的功能都已完成)<br/>


<h4>2018/2/7</h4>
1、大幅修改项目文件结构，包结构，测试运行正常<br/>
2、新增redis支持，到时申请redis服务器<br/>
3、重写服务层接口，完成首页部分接口编写（按接口文档序号）<br/>
4、清空controller层方法内代码，只保留方法<br/>
5、session移向服务层，并新增session通用操作<br/>
6、大幅修改dao层，逻辑不变<br/>
7、清除大量无意义工具类，部分合并，但未全部修改<br/>
8、将service api分离出来，为将来open-api/rpc-service-api？<br/>
9、将大部分类的操作抽象为BaseXXX接口<br/>

<h4>2018/1/27</h4>
1、大幅修改Dao层，更加具有结构化<br/>
2、删除pojo里的json包<br/>
3、添加了用户工厂类，但尚未实现<br/>

<h4>2018/1/21</h4>
1、完成邮箱等手机发送工具类<br/>
2、完成验证码工具类<br/>
3、添加ip工作类<br/>
4、部分工具类的正则验证尚未完成<br/>
5、其他层尚未完成<br/>

