<template>
  <div class="issueDiv">
    <h1 style="color : white;">Issue</h1>
    <div class="addIssue">
      <input type="text" class="issueTitle" v-model="this.$store.state.git.selectedFileName" readonly size="8">
      <input type="text" @keyup.enter="useAxiosGetData" class="issueText" size="30">
      <input type="button"  @click="useAxiosGetData" value="등록">
    </div>
    <hr>
    <div class="Issues">
      <div class="getIssue" v-for="a in $store.state.git.realIssue" :key="a">
        <span style="display : none;"> {{a.issueIdx}}</span>
        File : {{a.issueGitFile}} <br>
        Content : {{a.issueCn}} <br><br>
        {{a.member.memNick}}&nbsp;&nbsp;{{a.issueDate}}&nbsp;&nbsp;{{a.currentTime}}

        <select class="stateSelect" v-model="a.issueState" @change="updateIssueState">
          <option>버그</option>
          <option>수정중</option>
          <option>완료</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import moment from "moment"

export default {
  data() {
    return {
    }
  },
  mounted() {
  },
  methods : {
    ...mapMutations({
      addIssueData : 'git/addIssueData',
      setInsertedContent : 'git/setInsertedContent',
      getFileList : 'git/getFileList',
    }),
    updateIssueState(e){
      let selectedIndex = e.target.options.selectedIndex
      switch (selectedIndex) {
        case 0:
          selectedIndex = '버그'
          break;
        case 1:
          selectedIndex = '수정중'
          break;
        case 2:
          selectedIndex = '완료'
          break;
      }
      const url = '/gitAndIssue/updateIssue'

      this.axios.post( url, null, {
        params : {
          issueIdx : e.path[1].children[0].innerText,
          selectedIndex : selectedIndex,
        }
      })
          .then( (r)=>{
            console.log(r)
          })
    },
    useAxiosGetData(){
      let issueText = document.querySelector('.issueText').value;
      let issueTitle = document.querySelector('.issueTitle').value;

      if( issueText === '' || issueText === null) return
      if( issueTitle === '' || issueTitle === null) return

      const url = '/gitAndIssue/insert'

      this.axios.post( url, null, {
        params: {
          issueIdx: null,
          "project.prjctIdx": 1,
          "member.memIdx" : 1,
          issueCn: issueText,
          issueDate: moment().format('YYYY-MM-DD HH:mm:ss'),
          issueState: '버그',
          issueGitFile : issueTitle,
        }
      })
          .then( (r)=>{
                r.data.issueDate = r.data.issueDate.replace('T', ' ')
                this.$store.state.git.realIssue.unshift(r.data)
                document.querySelector('.issueText').value = ''
              }
          )
          .catch()
    },
  },
}
</script>

<style scoped>
.issueDiv > h1{
  font-size: 21px;
}
.addIssue{
  height: 4vh;
  background-color: #2C2F3B;
  display: flex;
  justify-content: space-between;
  color : #eee;
  border-radius: 5px;
}
.addIssue:hover{
  background-color: #414556;
}
.addIssue > input:nth-child(1),
.addIssue > input:nth-child(2){
  background: none;
  border: none;
  color: #eee;
  font-size: 18px;
  outline: none;
}
.addIssue > input:nth-child(2){
  width: 100%;
  outline: none;
  border: none;
}
.addIssue > input:nth-child(3){
  background-color: #FF8906;
  border-radius: 8px;
  width: 6vw;
  height: 70%;
  color: #eee;
  outline: none;
  border: none;
  cursor: pointer;
  align-self: center;
  margin-right: 8px;
  box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.5);
}
.Issues{
  height: 55vh;
  overflow: scroll;
}
.Issues::-webkit-scrollbar {
  display: none;
}
.Issues > div:nth-child(1){
  height: 14vh;
  margin-bottom: 12px;
}
.getIssue{
  background-color: #2C2F3B;
  height: 8vh;
  overflow: hidden;
  color : #eee;
  margin-top: 8px;
  border-radius: 5px;
  padding: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.getIssue:hover{
  background-color: #414556;
}
.stateSelect{
  background-color: #3F80A9;
  width: 6vw;
  color: #eee;
  height: 28px;
  border-radius: 8px;
  -webkit-appearance:none;
  -moz-appearance:none;
  appearance:none;
  text-align: center;
  border: none;
  box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.5);
}
.stateSelect:focus {
  outline: none;
}
.stateSelect:disabled {
  opacity: 0.5;
}
.stateSelect option{
  background: #C4C4C4;
  color: #fff;
  padding: 3px 0;
}

</style>