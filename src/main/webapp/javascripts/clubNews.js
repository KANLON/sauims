     (function(window) {
         var orgId = getParam("id");
         console.log(orgId);
         $.ajax({
                 url: '/index/club/' + orgId + "",
                 type: 'GET',
                 dataType: 'json',
             })
             .done(function(json) {

                 //将时间规范化
                 Date.prototype.toLocaleString = function() {
                     return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日";
                 };

                 $('#HeadTitle').text(json.data.orgName);
                 $('#HeadIntroduce').text(json.data.headIntroduce);
                 $('#HeadTime').text(new Date(json.data.foundTime).toLocaleString() + "  成立");
                 $('#bossName').text(json.data.adminName);
                 $('#mailboxName').text(json.data.email);
                 $('#telNum').text(json.data.phone);
                 $('#Introduce').text(json.data.description);
                 $('#HeadPic').attr("src", "/resource/logo/" + json.data.logo);

                 //绘图

                 //---------------------------------------------------------------------
                 // 基于准备好的dom，初始化echarts实例
                 var myChart = echarts.init(document.getElementById('Boypic1'));

                 // 指定图表的配置项和数据
                 var option = {
                     color: ['#37a2fe', '#8dcaea', '#327aa7'],
                     tooltip: {
                         trigger: 'item',
                         formatter: "{a} <br/>{b}: {c} ({d}%)"
                     },
                     legend: {
                         orient: 'vertical',
                         x: 'left',
                         data: ['男', '女']
                     },
                     series: [{
                         name: '男女比例',
                         type: 'pie',
                         radius: ['50%', '70%'],
                         avoidLabelOverlap: false,
                         label: {
                             normal: {
                                 show: false,
                                 position: 'center'
                             },
                             emphasis: {
                                 show: true,
                                 textStyle: {
                                     fontSize: '30',
                                     fontWeight: 'bold'
                                 }
                             }
                         },
                         labelLine: {
                             normal: {
                                 show: false
                             }
                         },
                         data: [{
                                 value: json.data.manNum,
                                 name: '男'
                             }, {
                                 value: json.data.womanNum,
                                 name: '女'
                             },

                         ]
                     }]
                 }

                 // 使用刚指定的配置项和数据显示图表。
                 myChart.setOption(option);


                 //绘图二
                 //----------------------------------------------------------------

                 // 基于准备好的dom，初始化echarts实例
                 var myChart2 = echarts.init(document.getElementById('Boypic2'));

                 // 指定图表的配置项和数据
                 var option2 = {
                     color: ['#37a2fe', '#8dcaea', '#327aa7', '#000FFF'],
                     tooltip: {
                         trigger: 'item',
                         formatter: "{a} <br/>{b}: {c} ({d}%)"
                     },
                     legend: {
                         orient: 'vertical',
                         x: 'left',
                         data: ['大一', '大二', '大三', '大四']
                     },
                     series: [{
                         name: '年级比例',
                         type: 'pie',
                         radius: ['50%', '70%'],
                         avoidLabelOverlap: false,
                         label: {
                             normal: {
                                 show: false,
                                 position: 'center'
                             },
                             emphasis: {
                                 show: true,
                                 textStyle: {
                                     fontSize: '30',
                                     fontWeight: 'bold'
                                 }
                             }
                         },
                         labelLine: {
                             normal: {
                                 show: false
                             }
                         },
                         data: [{
                             value: json.data.firstGradeNum,
                             name: '大一'
                         }, {
                             value: json.data.secondGradeNum,
                             name: '大二'
                         }, {
                             value: json.data.threeGradeNum,
                             name: '大三'
                         }, {
                             value: json.data.fourGradeNum,
                             name: '大四'
                         }, ]
                     }]
                 };


                 // 使用刚指定的配置项和数据显示图表。
                 myChart2.setOption(option2);


             })
             .fail(function() {
                 console.log("error");
             })
             .always(function() {
                 console.log("complete");
             });


         'use strict';

     })(window);

     /**
      * 获取指定的URL参数值
      * URL:http://www.quwan.com/index?name=tyler
      * 参数：paramName URL参数
      * 调用方法:getParam("name")
      * 返回值:tyler
      */
     function getParam(paramName) {
         paramValue = "", isFound = !1;
         if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
             arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
             while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
         }
         return paramValue == "" && (paramValue = null), paramValue
     }