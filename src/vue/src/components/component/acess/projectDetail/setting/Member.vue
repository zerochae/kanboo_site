<template>
  <div class="main-div">
    <div class="main-top">
      <div class="main-start-text">
        <h3>멤버 관리</h3>
      </div>
      <div class="insert-btn-div">
        <button class="insert-btn" v-if="this.$store.state.setting.clickState == false" @click="changeClickState()">수정</button>
        <button class="insert-btn" v-if="this.$store.state.setting.clickState == true" @click="changeClickState()">닫기</button>

      </div>
    </div>
    <div class="main-bottom">

      <ul class="member-ul">
        <div class="member-ul-div">
          <li v-for="(item,index) in this.$store.state.setting.projectMemberList" class="member-li" :key=index :id="`memberList-${index}`">
            <div class="member-li-div">
              <div class="member-li-img-div">
                <img :src="this.$store.state.setting.projectMemberList[index].member.img" class="member-img">
              </div>
              <div class="member-li-name-div">{{$store.state.setting.projectMemberList[index].member.nickname}}</div>
              <div class="member-li-ktag-div">{{$store.state.setting.projectMemberList[index].member.kTag}}</div>
              <div class="member-li-role-div">
                                <span :id="`${a}-span${index}`" v-for="(a,i) in this.$store.state.setting.roleList" :key=i
                                      @click="spanClick(`${a}-span${index}`, `${a}`, `${index}`)">
                                    {{a}}&nbsp;&nbsp;
                                </span>
              </div>
            </div>
          </li>
        </div>
      </ul>
    </div>

  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  data() {
    return {

      selectedStyle : {
        color : '#FF8906'
      },

    }
  },
  methods : {
    ...mapMutations({
      changeClickState : 'setting/changeClickState'
    }),
    isRoleMatch(){

      for(let i in this.$store.state.setting.projectMemberList){
        for(let j in this.$store.state.setting.projectMemberList[i].role){
          for(let k in this.$store.state.setting.roleList){
            if(this.$store.state.setting.projectMemberList[i].role[j] ==  this.$store.state.setting.roleList[k]){
              // this.$store.state.setting.projectMemberList[i].role.push(this.$store.state.setting.roleList[k]);
              document.getElementById(this.$store.state.setting.roleList[k] + '-span' + i).style.color = "red";
            }
          }
        }
      }
    },

    clickModifyBtn(){
      console.log(this.$store.state.setting.roleList)
      this.$store.state.setting.clickState = true;
    },
    spanClick(spanId, role, index){ // 클릭했을 때 이벤트 (배열에 데이터 넣음)
      const arr = [];
      const temp = {};
      temp[0] = arr;
      if(this.$store.state.setting.projectMemberList[index].role.includes(role)){
        for(var i in this.$store.state.setting.projectMemberList[index].role){
          if(this.$store.state.setting.projectMemberList[index].role[i] == role){
            if(role == "PM"){
              alert("PM은 삭제할 수 없습니다.")
              break;
            }
            document.getElementById(this.$store.state.setting.projectMemberList[index].role[i] + '-span' + index).style.color = "white";
            this.$store.state.setting.projectMemberList[index].role.splice(i, 1)
          }
        }
      }else if(role == "PM"){
        alert("PM은 추가할 수 없습니다.")
      }else{
        this.$store.state.setting.projectMemberList[index].role.push(role)
      }
      console.log(this.$store.state.setting.projectMemberList[index].role)
      this.isRoleMatch();
    },
  },

  mounted() {

    this.isRoleMatch();



  },
}
</script>

<style scoped>
.main-div{
  border-radius : 5px;
  background-color : #2C2F3B;
  color : white;
  width: 60vw;
  height : 250px;

  display : flex;
  justify-content : flex-start;
  /* align-items : center; */
  flex-direction : column;
  /* white-space: nowrap; */
  font-size: 20px;
  overflow : hidden;
  margin-top : auto;
  margin-bottom : auto;


}
.main-top{
  display : flex;
  align-items : stretch;
  justify-content: space-between;
  margin : 10px;
  height : 20%;
}
.main-bottom{
  height : 80%;
  display : flex;
  justify-content: space-between;

}
.insert-btn-div{
  border-radius: 5px;
  background-color : #FF8906;
  display : inline-block;
  margin-right : 10px;
  margin-top : 10px;
  color : white;
}

.member-ul-div{
  width: 100%;
  height : 210px;
}

.member-li-ktag-div{
  margin-left: 10px;
}

.member-li-div{
  display: flex;
  justify-content: space-around;
  align-items: stretch;
  margin-left: 20px;

  /* flex-direction: row; */
}
.member-li-role-div{
  margin-left : 50px;

}
.member-li{
  margin-left: 10px;
  margin-bottom : 20px;
}
.member-li-img-div{
  border-radius: 70%;
  overflow: hidden;
  margin-right: 10px;
}
.member-img{
  width: 40px;
  height: 40px;
}

.member-ul{
  overflow-x: hidden;
  overflow-y: auto;

}
.member-ul::-webkit-scrollbar{
  display: none;
}

span{
  margin-right: 5px;
}

.orange-color{
  color : #FF8906;
}

</style>