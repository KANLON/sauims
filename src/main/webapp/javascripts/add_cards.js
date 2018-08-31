(function(window) {
  'use strict';
  var $ = window.jQuery;
  var Mock = window.Mock;
  var template;
  // var App=window.App || {};
  var orgId;
  $.ajax({
      url: '/index/club',
      type: 'GET',
      dataType: 'json',
    })
    .done(function(json) {
      console.log(json);
      template = json;
      let Data = Mock.mock(template);
      for (let i = 0; i < Data.data.length; i++) {
        if (Data.data[i].orgId === 11) {
          let $li = $(`
        <!-- li begin -->
        <li>
          <!--  -->
          <div class="shade"></div>
          <!--  -->
          <div class="cover pos">
          
            <!-- 如果社团id，orgId ===11，就是校社联用户。 如果是社团的话，就是下面的展示图的链接，如果是校社联的话，就是另一个链接 -->
            <a href="/clubNews.html?id=${Data.data[i].orgId}" target="_blank" title="${Data.data[i].orgName}">
              <img src="/resource/view/sau/${Data.data[i].view}" alt="${Data.data[i].view}">
              
            </a>
          </div>
          <div class="info">
            <h4 class="ta">${Data.data[i].orgName}</h4>
            <div class="msg mtn cl ta">
              ${Data.data[i].description}
            </div>
            <p class="mtn cl">
              <img src="./images/member.png" alt="成员数" class="icon-member">
              <span class="members">${Data.data[i].members}人</span>
              <img src="./images/heart.png" alt="喜欢数" class="icon-heart">
              <span class="likeNumber">${Data.data[i].likeClick}人</span>
            </p>
          </div>
        </li>`);
          $li.appendTo('ul');
        } else {
          let $li = $(`
        <!-- li begin -->
        <li>
          <!--  -->
          <div class="shade"></div>
          <!--  -->
          <div class="cover pos">
          
            <!-- 如果社团id，orgId ===11，就是校社联用户。 如果是社团的话，就是下面的展示图的链接，如果是校社联的话，就是另一个链接 -->
            <a href="/clubNews.html?id=${Data.data[i].orgId}" target="_blank" title="${Data.data[i].orgName}">
              <img src="/resource/view/club/${Data.data[i].view}" alt="${Data.data[i].view}">
              
            </a>
          </div>
          <div class="info">
            <h4 class="ta">${Data.data[i].orgName}</h4>
            <div class="msg mtn cl ta">
              ${Data.data[i].description}
            </div>
            <p class="mtn cl">
              <img src="./images/member.png" alt="成员数" class="icon-member">
              <span class="members">${Data.data[i].members}人</span>
              <img src="./images/heart.png" alt="喜欢数" class="icon-heart">
              <span class="likeNumber">${Data.data[i].likeClick}人</span>
            </p>
          </div>
        </li>`);
          $li.appendTo('ul');
        }

      }

      console.log("success");
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });



  /*
        function DataId () {
        return orgId;
      }
      App.DataId=DataId;
      window.App=App;
  */
}(window));