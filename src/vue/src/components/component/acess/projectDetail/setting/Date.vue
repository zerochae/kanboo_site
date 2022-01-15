<template>
  <div class="project-date-div" >
    <div class="modal-background" v-if="modalState == true" >
      <vue-cal
          class="vuecal--date-picker"
          xsmall
          hide-view-selector
          :time="false"
          :transitions="false"
          @cell-click="selectedDate"
          active-view="month"
          :disable-views="['week', 'day']"
          style="width: 250px;height: 250px">
      </vue-cal>
    </div>

    <div class="text-div">
      프로젝트 기간 변경
    </div>
    <div class="date-div">
      <label for="start-date">Start</label>&nbsp;
      <input type="text" class="date-input" id="start-date" @click="clickStartDateFunction" v-model="inputStartDate">&nbsp;
      <label for="end-date">End</label>&nbsp;
      <input type="text" class="date-input" id="end-date" @click="clickEndDateFunction" v-model="inputEndDate">
    </div>
  </div>
</template>

<script>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import moment from 'moment'

export default {
  data() {
    return {
      inputStartDate : "",
      inputEndDate : "",
      clickStart : false,
      clickEnd : false,
      modalState : false,

    }
  },
  components : {
    VueCal,
  },
  methods : {
    selectedDate(e){

      // console.log(moment(e).format('YYYY/MM/DD'))
      // this.selectedDate = moment(e).format('YYYY/MM/DD')
      if(this.clickStart == true){
        this.inputStartDate = moment(e).format('YYYY/MM/DD')
        this.$store.state.setting.projectDate.startDate = moment(e).format('YYYY/MM/DD')
        if(this.checkDate(this.inputStartDate, this.$store.state.setting.projectDate.endDate) == false){
          this.inputStartDate = ""
        }
      }else if(this.clickEnd == true){
        this.inputEndDate = moment(e).format('YYYY/MM/DD')
        this.$store.state.setting.projectDate.endDate = moment(e).format('YYYY/MM/DD')
        if(this.checkDate(this.$store.state.setting.projectDate.startDate, this.inputEndDate) == false){
          this.inputEndDate = ""
        }
      }
      console.log(this.$store.state.setting.projectDate)
      this.modalState = false;
      this.clickStart = false;
      this.clickEnd = false;
      this.checkDate(this.inputStartDate, this.inputEndDate)
    },
    clickStartDateFunction(){
      if(this.modalState == false){ this.modalState = true; this.clickStart = true;}

      else {this.modalState = false; }
    },
    clickEndDateFunction(){
      if(this.modalState == false){ this.modalState = true; this.clickEnd = true;}
      else this.modalState = false
    },
    loadStoreDate(){
      this.inputStartDate = this.$store.state.setting.projectDate.startDate;
      this.inputEndDate = this.$store.state.setting.projectDate.endDate;
    },
    checkDate(start, end){
      var startDate = moment(start).format('YYYYMMDD')
      var endDate = moment(end).format('YYYYMMDD')
      if(endDate - startDate < 0){
        alert("날짜가 잘못됐다...")
        return false;
      } else if(startDate == 0 && startDate == null && endDate == 0 && endDate == null){
        return false;
      }
    }


  },
  mounted() {
    this.loadStoreDate();
  }
}
</script>

<style scoped>
.project-date-div{
  position :relative;
  border-radius : 5px;
  padding : 15px;
  display :flex;
  justify-content : space-around;
  flex-direction : column;
  width: 60vw;
  height : 120px;
  box-sizing: border-box;
  background-color : #2C2F3B;
  color : white;
  /* margin-top: auto; */
  margin-bottom : auto;
}
.date-div{

  color : black;

}
.date-input{
  background-color : #414556;
  border : 0px;
  border-radius : 5px;
  width : 300px;
  height : 30px;


}
.modal-background{
  position: absolute;
  width: 18.5vw; height:30vh;
  margin-left: 50vh;


}
.vuecal--date-picker{
  width: 100%;
  background: #646464;
  border-radius: 8px;
  padding: 20px;
}
</style>