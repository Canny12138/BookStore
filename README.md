# 基于SpringBoot的图书商城系统
# 移动客户端的实现
## 1.1 登录与注册
用户需要注册并登录后才能使用购物车、收藏夹等功能。在登录/注册页面，用户可以输入用户名、密码、邮箱等信息进行注册。注册成功后，用户可以使用用户名和密码登录系统。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/3c3c86cb-47cb-4699-863f-40fbfc5855ca)
![image](https://github.com/Canny12138/BookStore/assets/59813917/a2b10f32-9448-43cf-aeed-385169ad1823)  
## 1.2 首页
首页展示了图书商城的推荐图书、热门图书等内容。用户可以通过首页轻松查看当前最受欢迎的图书。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/5d22e57e-d921-4e1e-a0ee-10d557c8b11f)  
## 1.3 分类
分类界面展示了图书商城中所有的图书分类。用户可以根据兴趣，点击相应的分类查看该分类下的所有图书。在顶部，用户可以使用搜索功能根据关键字搜索图书。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/f6ba5571-3e20-4c87-9f71-c769b70ea526)  
## 1.4 商品详情
图书详情界面展示了图书的详细信息，如书名、价格、库存、封面图片和详细描述等。用户可以在此页面收藏图书、将图书添加到购物车或立即购买。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/33d2e013-748e-4ce8-a6b2-b95ab51d4ac0)  
## 1.5 个人中心
个人中心界面为用户提供了查看个人信息、收货地址管理、收藏夹管理、订单管理等功能。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/9f6cd2a3-eba6-498c-8791-4cacec9a2953)  
## 1.6 收藏夹
收藏夹界面展示了用户收藏的所有图书。在这个界面上，每本图书都包含了图书封面、价格等基本信息。用户可以直接从收藏夹中进入图书详情页面，或者将收藏的图书添加到购物车。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/70ae511a-8d6c-431f-a4e6-e49d72f2a900)  
## 1.7 购物车
购物车界面展示了用户添加到购物车中的所有图书。用户可以在此页面修改购物车中的图书数量、删除图书或者直接提交订单。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/2e3598b1-81f1-4c6e-b3db-a238e78d8eda)  
## 1.8 订单确认
当用户从购物车页面提交订单时，将进入订单确认界面。在此界面，用户可以选择收货地址、查看订单中的图书列表和总金额，然后确认提交订单。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/3e6063b3-02d5-4675-86bf-92b796528e03)  
## 1.9 订单列表
订单列表界面展示了用户在图书商城中的所有订单，用户可以在此界面查看待支付、待发货、待收货、待评价等不同状态的订单。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/ad2d54e5-0f5a-4e75-8ea7-bfe1f53e7507)  
## 1.10 地址列表
在个人中心的地址列表界面中，用户可以浏览地址和点击编辑收货地址进入收货地址编辑页面。在此页面，用户可以修改收货地址的详细信息，如收件人姓名、电话、详细地址等。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/de1ec0b8-8c7a-43a5-8bba-fa6fb0007746)
![image](https://github.com/Canny12138/BookStore/assets/59813917/39140080-efe5-4f99-bc6f-7db91d1f84b0)  
## 1.11 秒杀商品
在商品界面中，用户浏览正在限时秒杀促销的商品列表，点击进入秒杀商品详情页，可以查看秒杀特价和秒杀倒计时。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/4e912211-1b16-4497-a520-ffa56095ccec)
![image](https://github.com/Canny12138/BookStore/assets/59813917/3ed824fa-16c8-4c8e-a653-c72034e730e8)  
# 后台管理系统的实现
## 2.1 用户管理界面
用户管理界面展示了系统中所有用户的列表。管理员可以在此界面查看、添加、修改和删除用户。每个用户的信息包括用户名、创建时间等。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/52d26c21-b1bb-4864-84e6-02af05b66797)  
![image](https://github.com/Canny12138/BookStore/assets/59813917/5f6085ec-a2e0-4a49-b8af-42ed1ca3859e)  
# 商家后台系统的实现
## 3.1 商品管理界面
商品管理界面展示了店铺中所有图书的列表。商家可以在此界面查看、添加、修改和删除图书。每本图书的信息包括书名、价格、库存等。  
![image](https://github.com/Canny12138/BookStore/assets/59813917/5d349fa9-5146-4448-9db6-a0ada04c0115)  
![image](https://github.com/Canny12138/BookStore/assets/59813917/97d00cba-89e0-4eb4-907d-0b75247f38ad)  
## 3.2 订单管理界面
商品管理界面展示了店铺中所有订单的列表。商家可以在此界面查看订单，并对未发货的订单进行发货操作。   
![image](https://github.com/Canny12138/BookStore/assets/59813917/547d106c-c311-46f2-82ce-795dab61ceb4)  


