// Mock.mock( template )
var myJsonData =null;
function getMyData(){
    var url = "/SAU/index/club";
    var sendData = null;
    $("#output1").text(sendData);
    $.get(url,sendData,function(backData,textStaut,ajax){
        myJsonData = ajax.responseText;
        console.log(myJsonData);
         alert(myJsonData);
        let template = myJsonData;
        let Data = JSON.parse(template);
        for (let j = 0; j < 5; j++) {
            for (let i = 0; i < Data.data.length; i++) {
                let $li = $(`
      <!-- li begin -->
      <li>
        <!--  -->
        <div class="shade"></div>
        <!--  -->
        <div class="cover pos">
          <a href="" target="_blank" title="">
            <img src="./images/${Data.data[i].clubView}" alt="${Data.data[i].clubView}">
          </a>
        </div>
        <div class="info">
          <h4 class="ta">${Data.data[i].clubName}</h4>
          <div class="msg mtn cl ta">
            ${Data.data[i].description}
          </div>
          <p class="mtn cl">
            <img src="./images/member.png" alt="成员数" class="icon-member">
            <span class="members">${Data.data[i].members}人</span>
            <img src="./images/heart.png" alt="喜欢数" class="icon-heart">
            <span class="likeNumber">${Data.data[i].likeNumber}人</span>
          </p>
        </div>
      </li>`);
                $li.appendTo('ul');
            }
        }
// });
    })
    };
getMyData();
