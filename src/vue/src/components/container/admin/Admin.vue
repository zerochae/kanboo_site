<template>
  <div class="admin-container">
    <div class="middle">
    <UserProject class="userProject-container"/>
    <UserList class="userList-container"/>
    </div>
    <QnA class="qna-container"/>
  </div>
</template>

<script>
import QnA from "../../component/admin/QnA.vue";
import UserList from "../../component/admin/UserList.vue";
import UserProject from "../../component/admin/UserProject.vue";
export default {
  components: { 
    QnA, 
    UserList, 
    UserProject 
    },
};
</script>

<style>

.admin-container{
height: calc(100vh - 70px);
display: flex;
padding: 25px;
}

.userProject-container{
  width: 50vw;
  height: 50%;
  padding: 25px;
  overflow: hidden;
}

.userList-container{
  width: 50vw;
  height: 50%;
  padding: 25px;
  overflow: hidden;
}

.qna-container{
  width: 50vw;
  padding: 25px;
  overflow: hidden;
}

@media (max-width:1450px) {
  .admin-container{
    display: flex;
  }

  .userProject-container{
    width: 50vw;
    height: 50%;
    float: left;
    overflow: hidden;
  }

  .userList-container{
    width: 50vw;
    height: 50%;
    float: left;
    overflow: hidden;
  }
  .qna-container{
    width: 100%;
    height: 100%;
    float: right;
    overflow: hidden;

  }
}

@media (max-width:1268px) {
  .admin-container{
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  .userProject-container{
    width: 100%;
    height: 50%;
    float: left;
    overflow: hidden;
  }

  .userList-container{
    width: 100%;
    height: 50%;
    float: left;
    overflow: hidden;
  }
  .qna-container{
    width: 100%;
    height: 100%;
    float: right;
    overflow: hidden;
  }
}
@media (max-width:768px){
  .userProject-container{
    width: 100%;
    height: 50%;
    overflow: hidden;
  }

  .userList-container{
    width: 100%;
    height: 50%;
    overflow: hidden;
  }
  .qna-container{
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
}

@media (max-width:535px) {
  
}


</style>