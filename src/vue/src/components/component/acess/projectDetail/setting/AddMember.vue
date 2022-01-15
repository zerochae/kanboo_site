<template>
  <div class="main-div">
    <div class="main-left-div">
      <div class="main-start-text"><h3>멤버 추가</h3></div>
      <ul class="select-member-list" id="select-member-list">
        <li v-for="(item, index) in this.$store.state.setting.selectMemberList" :key="index" class="select-member-info"
            id="select-member-info(`${index}`)">
          <div class="select-member-image">
            <img :src="this.$store.state.setting.selectMemberList[index].img" class="member-image"/>
          </div>
          <div class="select-member-text">
            {{$store.state.setting.selectMemberList[index].nickname}}
            {{$store.state.setting.selectMemberList[index].kTag}}
          </div>&nbsp;
          <div class="select-member-job">
            <select @v-model="state.selectedPosition">
              <option v-for="(a, i) in this.$store.state.setting.roleList" :key="i" value="a"
                      @click="setMemberRole(this.$store.state.setting.selectMemberList[i].idx, a)">{{a}}</option>
              <!-- <option value="PL">PL</option>
              <option value="DA">DA</option>
              <option value="TA">TA</option>
              <option value="AA">AA</option>
              <option value="UA">UA</option>
              <option value="BA">BA</option>
              <option value="EA">EA</option>
              <option value="SA">SA</option> -->
            </select>
          </div>
          <div class="cancel-btn">
            <button id="`${select-member-insert-cancel-btn[index]}`"
                    @click = "insertCancel(this.$store.state.setting.selectMemberList[index].idx,
                    this.$store.state.setting.selectMemberList[index].nickname, this.$store.state.setting.selectMemberList[index].kTag,
                    this.$store.state.setting.selectMemberList[index].img, index), leftTextHideFunction()" >x</button>
          </div>
        </li>
      </ul>
      <div id="plzClick" class="plzClick">
        우측에서 검색 후 추가할 멤버를 클릭해 주세요
      </div>
      <div>
        <button class="select-member-insert-btn" @click="testBtn">멤버 추가</button>
      </div>
    </div>

    <!-- <div class="main-center-div">
        test2

    </div> -->

    <div class="main-right-div">
      <input type="text" class="search-input" id="search-member" v-model="inputName" @keyup="searchName">
      <ul class="search-member-result-list">
        <li id="selectMember(index)" @click="searchMemberSelect(this.$store.state.setting.searchMemberList[index].nickname,
             this.$store.state.setting.searchMemberList[index].kTag, this.$store.state.setting.searchMemberList[index].img,
              this.$store.state.setting.searchMemberList[index].idx, index),
             leftTextHideFunction()" v-for="(item, index) in
             this.$store.state.setting.searchMemberList" :key="item" class="search-member-result">
          <div class="search-member-result-image">
            <img :src="this.$store.state.setting.searchMemberList[index].img" class="member-image"/>
          </div>
          <div class="search-member-result-text">
            {{$store.state.setting.searchMemberList[index].nickname}}&nbsp;
            {{$store.state.setting.searchMemberList[index].kTag}}
          </div>
        </li>
      </ul>
    </div>
    <!-- <img src="../../../../../testData/0.png"/>
    <img src="@/testData/0.png"/> -->
  </div>
</template>

<script>

import axios from 'axios';

export default {
  methods: {
    setMemberRole(idx, position){
      alert("들어옴")
      for(var i in this.$store.state.setting.selectMemberList[i]){
        if(this.$store.state.setting.selectMemberList[i].idx == idx){
          this.$store.state.setting.selectMemberList[i].position = position;
        }
      }
    },
    testBtn(){
      console.log(this.$store.state.setting.selectMemberList)
    },

    searchName: function(){
      this.$store.state.setting.searchMemberList = [];
      axios.post('', this.searchName).then(res =>{//나중에 URL 기입
        this.$store.state.setting.searchMemberList.push(res.data)

      })

    },



    leftTextHideFunction(){
      if((Object.keys(this.$store.state.setting.selectMemberList).length) >0){
        document.getElementById('plzClick').style.display = 'none';
      } else{
        document.getElementById('plzClick').style.display = 'block';
      }
    },
    insertCancel(idx,nickname,kTag,img,index){
      const temp = {
        nickname : nickname,
        kTag : kTag,
        img : img,
        idx : idx
      }

      this.$store.state.setting.searchMemberList.push(temp);
      this.$store.state.setting.selectMemberList.splice(index, 1);
    },
    searchMemberSelect(nickname, kTag, img, idx, index){
      // console.log("test : " + JSON.stringify(this.searchMember));
      const temp = {
        nickname : nickname,
        kTag : kTag,
        img : img,
        idx : idx
      }
      this.$store.state.setting.searchMemberList.splice(index, 1)
      this.$store.state.setting.selectMemberList.push(temp);
    }
  },


  mounted() {
    this.leftTextHideFunction();

    window.addEventListener("keyup", (e)=>{
      e;
      this.axios.post('', this.inputName).then(res =>{
        this.resultMember.push(res.data);

      })


    })

  },

  data() {
    return{
      receivedData : false,
      inputName : "",
      selectMember : [

      ],
      searchMember : [



      ],
    }
  }
}
</script>

<style scoped>
.main-div{
  border-radius : 5px;
  padding : 15px;
  background-color : #2C2F3B;
  display : flex;
  justify-content : space-around;
  align-items : flex-start;
  color : white;
  width : 60vw;
  margin-top : 20px;
  margin-bottom : 20px;
  height : 250px;


}
.main-left-div{
  margin-left: 20px;
  display : flex;
  justify-content: space-between;
  align-items : stretch;
  flex-direction : column;
  width : 50%;
  height : 100%;
}

.main-right-div{
  width : 50%;
  margin-left: 20px;
}

.select-member-info{
  display : flex;
  justify-content : space-around;
  margin : 4px;
  padding : 5px;
  white-space:nowrap;

}
.select-member-image{
  border-radius: 70%;
  overflow: hidden;
  margin-right: 10px;
}
.member-image{
  width: 20px;
  height : 20px;
}

.search-member-result-image{
  border-radius: 70%;
  overflow: hidden;
  margin-right: 10px;
}

.plzClick{
  margin-bottom: 30px;
  font-size: 16px;
}


.select-member-job *{
  color : #FF8906;
  background-color : #2C2F3B;
  font-size: 16px;
  -o-appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  border: none;
  position: relative;
}

.search-input{
  background-color : #414556;
  border : 0px;
  border-radius : 5px;
  width : 170px;
  height : 30px;
  margin : 10px;
}
.search-member-result-list{
  overflow-x: hidden;
  overflow-y: auto;
  white-space: nowrap;
  display: grid;
  grid-template-columns: 1fr 1fr ;
  grid-auto-rows: 30px;
  margin-top : 20px;
  width: 400px;
  height: 200px;
  font-size : 20px;

}
.search-member-result-list::-webkit-scrollbar{
  display: none;
}
.search-member-result{
  display : flex;
  color : white;
  padding : 3px;
}
.select-member-insert-btn{
  border-radius: 5px;
  width : 80px;
  height : 30px;
  background-color : #FF8906;
  margin-top : 5px;
}

.select-member-list{
  display : flex;
  flex-direction: column;
  justify-content : flex-start;
  margin-bottom : auto;
  align-items: baseline;
  overflow-x: hidden;
  overflow-y: auto;
}
.select-member-list::-webkit-scrollbar{
  display: none;
}

.main-start-text{
  margin: 10px;
  font-size : 22px;
}
</style>