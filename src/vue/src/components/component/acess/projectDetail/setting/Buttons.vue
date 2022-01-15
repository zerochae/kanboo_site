<template>
  <div>
    <button class="modify-btn" @click="postModify">수정</button>
    <button class="end-btn" @click="postEnd">Project End</button>
    <button class="delete-btn" @click="postDelete">Project Delete</button>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  methods :{
    postModify(){
      if(confirm("수정할거여?")){
        axios.post('',{
          params : {
            selectMemberList : this.$store.state.setting.selectMemberList,
            startDate :  this.$store.state.setting.startDate,
            endDate : this.$store.state.setting.endDate
          }
        }).then(res =>{
          console.log(res.data);
          alert("정상적으로 수정되었습니다..");
        }).catch(err =>{
          console.log(err.data);
          alert("수정에 실패했습니다.");
        });
      }else{
        alert("취소했음");
      }

    },

    postDelete(){
      if(confirm("삭제할거여?")){
        axios.post('', {
          params : {
            projectIdx : this.$store.state.setting.projectIdx
          }
        }).then(res =>{
          console.log(res.data);
          alert("정상적으로 수정되었습니다..");
        }).catch(err =>{
          console.log(err.data);
          alert("수정에 실패했습니다.");
        });

      }else{
        alert("취소했음")
      }
    },
    postEnd(){
      if(confirm("마칠거여??")){
        axios.post('', {
          params : {
            projectIdx : this.$store.state.setting.projectIdx
          }
        }).then(res =>{
          console.log(res.data);
          alert("정상적으로 수정되었습니다..");
        }).catch(err =>{
          console.log(err.data);
          alert("수정에 실패했습니다.");
        });

      }else{
        alert("취소했음")
      }
    },

  }
}
</script>

<style scoped>
div{

  display :flex;
  justify-content : flex-end;
  align-items : stretch;
  flex-direction: row;
  width : 60vw;
  height : 100px;

}
.modify-btn{
  background-color : #FF8906;
  width : 100px;
  height : 30px;
  margin : 20px;
  border-radius : 5px;
}

.end-btn{
  background-color : #FF8906;
  width : 180px;
  height : 30px;
  margin : 20px;
  border-radius : 5px;

}

.delete-btn{
  background-color : #FF5C5C;
  width : 180px;
  height : 30px;
  margin : 20px;
  border-radius : 5px;

}
</style>