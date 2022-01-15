<template >
  <div class="black-bg" v-if="this.isModal" @click="closeModal">
    <div class="white-bg">
      <div class="white-bg-left" >
        <vue-cal
            xsmall
            locale="ko"
            :time="false"
            hide-view-selector
            active-view="month"
            @cell-click="this.getStartDate"
            :disable-views="['years','year','week', 'day']"
            class="vuecal--blue-theme vuecal--rounded-theme startDatePicker"
            style="width: 300px ;height: 280px"
            :style="{zIndex: startZIndex}">
        </vue-cal>

        <vue-cal
            xsmall
            locale="ko"
            :time="false"
            hide-view-selector
            active-view="month"
            @cell-click="this.getEndDate"
            :disable-views="['years','year','week', 'day']"
            class="vuecal--blue-theme vuecal--rounded-theme endDatePicker"
            style="width: 300px ;height: 280px"
            :style="{zIndex: endZIndex}">
        </vue-cal>
      </div>

      <div class="white-bg-right">

        <input class="titleInput" type="text"  placeholder="Enter the Title..." v-model="this.eventTitle">
        <textarea class="contentInput" rows="100" cols="100"  placeholder="Enter the Detail..." v-model="this.eventContent"></textarea>

        <div class="timePicker">
          <span>
            <input class="startDate" type="text" placeholder="Start Date..." @click="setStartIndex" v-model="this.startDate" readonly>
          </span>
          <span>
            <input class="endDate" type="text" placeholder="End Date..." @click="setEndIndex" v-model="this.endDate" readonly>
          </span>

          <vue-timepicker :minute-range="[0, 6, [10, 30], 42, 50]"  drop-direction="auto"  auto-scroll v-model="this.startTimePicker"></vue-timepicker>
          <vue-timepicker :minute-range="[0, 6, [10, 30], 42, 50]"  drop-direction="auto"  auto-scroll v-model="this.endTimePicker"></vue-timepicker>
        </div>

        <div class="rightInput">
          <input  type="checkbox" id="isAllDay" v-model="this.isAllDay" >
          <label for="isAllDay" >IsAllDay...?</label>
          <input  type="checkbox" id="delete" v-model="this.deleteflag" >
          <label for="delete" >Don't Delete...!</label>
          <input  type="checkbox" id="resize" v-model="this.resizeflag" >
          <label for="resize" >Don't Resize...!</label>
        </div>

        <div class="classification-btn" style="display : flex; ">
          <div  v-for="(a, i) in this.buttonText" :key="i" >
            <button v-if="a === this.clickedValue" style="color : #FF8906; width : 100%; "
                    @click="filterClick" class="filterBtn">
              {{a}}</button>

            <button v-else style="width : 100%; "
                    @click="filterClick" class="filterBtn">
              {{a}}</button>

          </div>
        </div>

        <div class="finish-wrap">
          <button id="cancelBtn" class="closeModalBtn" @click="this.resetValue()">CANCEL</button>
          <button id="createBtn" class="closeModalBtn" @click="this.createEventUseModal()" >CREATE</button>
        </div>


      </div>

    </div>
  </div>

  <div class="schedulerContainer">
    <div class="leftDiv">
      <div class="scheduler">
        <MonthCalendar/>
        <br>
        <br>
        <!-- <i  style="color: #eee; font-size:50px " @click="this.setModalTrue"
            class="fab fa-apple"></i>
        <button @click="setValue" style="color : #eee;">모달창</button>
        <br>
        <input type="button" id="changeTheme" value="테마바꾸기">
        <input type="button" id="changeLang" value="언어바꾸기"> -->
      </div>
      <div class="filter">
        <Filter/>
      </div>
    </div>

    <div class="rightDiv">
      <WeekCalendar/>
    </div>
  </div>
</template>

<script>

// npm install vue3-timepicker --save

import MonthCalendar from '@/components/component/acess/projectDetail/scheduler/MonthCalendar.vue'
import WeekCalendar from '@/components/component/acess/projectDetail/scheduler/WeekCalendar.vue'
import Filter from '@/components/component/acess/projectDetail/scheduler/Filter.vue'
import { mapMutations, mapState } from 'vuex'

import VueTimepicker from 'vue3-timepicker'
import 'vue3-timepicker/dist/VueTimepicker.css'
import VueCal from 'vue-cal'

import '../../../../../node_modules/vue-cal/dist/vuecal.css'
import '../../../../../node_modules/vue-cal/dist/drag-and-drop.js'
import '../../../../assets/js/ko.js'
import '../../../../assets/css/blackTheme.css'
export default {
  name: 'Scheduler',
  components: {
    MonthCalendar,
    WeekCalendar,
    Filter,
    VueTimepicker,
    VueCal,
  },
  data() {
    return {
      startZIndex : 1,
      endZIndex : 0,

      // modal variables
      flagStartDate : false,
      flagEndDate : false,

      startTimePicker : '',
      endTimePicker : '',
      endDate : '',
      startDate :'',
      eventTitle : '',
      eventContent : '',
      selectedClass : '',

      clickedValue : '공통',

      isAllDay : '',
      deleteflag : '',
      resizeflag : '',

      filterValue : '',

      buttonText : [
        '공통',
        '개인',
        '공지',
        '긴급',
        '휴가',
        '기타'
      ],
      // modal variables

    }
  },
  computed : {
    ...mapState({
      isModal : state => state.scheduler.isModal
    })
  },
  methods: {
    ...mapMutations({
      showData : 'scheduler/showData',
      closeModal : 'scheduler/closeModal',
      setCallAddFunction : 'scheduler/setCallAddFunction',
      setFlagStartDate : 'scheduler/setFlagStartDate',
      setflagEndDate : 'scheduler/setflagEndDate',
      setModalTrue : 'scheduler/setModalTrue',
      resetValue : 'scheduler/resetValue',
      setModalFalse : 'scheduler/setModalFalse',
      pushData : 'scheduler/pushData',
    }),

    // modal functions start
    // 필터 클릭시 색 변경
    filterClick(e){
      if(e.type === 'click'){
        let buttons = document.querySelectorAll('button.filterBtn')
        for(let i = 0; i< buttons.length; i++){
          buttons[i].style.color = '#fff';
        }
        e.path[0].style.color = "#FF8906"
        this.setClickedValue(e.target.innerHTML)
      }
    },
    setStartIndex(){
      this.endZIndex = 0
      this.startZIndex = 1
      this.setFlagStartDate(true)
    },
    setEndIndex(){
      this.endZIndex = 1
      this.startZIndex = 0
      this.setFlagEndDate(true)
    },
    getStartDate(e){
      this.flagStartDate = !this.flagStartDate
      this.startDate = e.format('YYYY-MM-DD')
    },
    getEndDate(e){
      this.flagEndDate = !this.flagEndDate
      this.endDate = e.format('YYYY-MM-DD')
    },
    setFlagStartDate(e){
      this.flagStartDate = e
    },
    setFlagEndDate(e){
      this.flagEndDate = e
    },
    setClickedValue(e){
      this.clickedValue = e
    },
    resetValue(){
      this.startTimePicker = ''
      this.endTimePicker = ''
      this.endDate = ''
      this.startDate = ''
      this.eventTitle = ''
      this.eventContent = ''
      this.selectedClass = ''
      this.isAllDay = ''
      this.deleteflag = ''
      this.resizeflag = ''
      this.clickedValue = '공통'
      this.startZIndex = 1
      this.endZIndex = 0
    },
    createEventUseModal(){
      const copy = [...this.$store.state.scheduler.data]
      const url = '/calendar/insertSchedule'
      let codeDetailIdx
      let temp
      let v_allDay
      let v_delete
      let delTemp
      let v_resize
      let reTemp

      switch (this.clickedValue) {
        case '공통':
          this.clickedValue = 'common'
          codeDetailIdx = 1
          break;
        case '개인':
          this.clickedValue = 'individual'
          codeDetailIdx = 2
          break;
        case '공지':
          this.clickedValue = 'notice'
          codeDetailIdx = 3
          break;
        case '긴급':
          this.clickedValue = 'emergency'
          codeDetailIdx = 4
          break;
        case '휴가':
          this.clickedValue = 'vacation'
          codeDetailIdx = 5
          break;
        case '기타':
          this.clickedValue = 'note'
          codeDetailIdx = 6
          break;
      }

      if(this.isAllDay !== true){
        temp = false
        v_allDay = 'n'
      }else{
        v_allDay = 'y'
        temp = true
      }

      if(this.deleteflag !== ''){
        v_delete = 'y'
        delTemp = false
      }else{
        v_delete = 'n'
        delTemp = true
      }

      if(this.resizeflag !== ''){
        v_resize = 'y'
        reTemp = false
      }else{
        v_resize = 'n'
        reTemp = true
      }

      if(this.startTimePicker === this.endTimePicker || this.startTimePicker > this.endTimePicker){
        this.resetValue()
        return
      }

      const arr ={
        id : '',
        start : this.startDate +' '+ this.startTimePicker,
        end : this.endDate + ' ' + this.endTimePicker,
        title : this.eventTitle,
        content : this.eventContent,
        class : this.clickedValue,
        deletable: delTemp,
        resizable: reTemp,
        draggable: reTemp,
        allDay : temp,

      }
      this.pushData(arr)

      if(copy.length !== this.$store.state.scheduler.data.length){
        this.resetValue()
      }

      this.axios.post( url, null, {
        params : {
          calIdx : null,
          'project.prjctIdx' : 1,
          'member.memIdx' : 3,
          calStartDate : arr.start,
          calEndDate : arr.end,
          calColor : arr.class,
          calCn : arr.content,
          calTitle : arr.title,
          calDelAt : 'n',
          calIsAllDay : v_allDay,
          calIsDeletable : v_delete,
          calIsResizable : v_resize,
          'codeDetail.codeDetailIdx' : codeDetailIdx,
          'codeDetail.masterCode.masterCodeIdx' : 'CAL',
        }
      })
          .then( r => {
            this.setModalFalse()
            let index = this.$store.state.scheduler.data.length-1
            this.$store.state.scheduler.data[index].id = r.data.calIdx
          })
    },
    // modal Functions end
  },
}

</script>

<style scoped>
*{
  font-size: 15px;
}

/* vw로 가변넓이  */
.schedulerContainer{
  width: 100%;
  height: calc(100vh - 70px);
  display: flex;
  justify-content: space-between;
  padding : 40px;
  padding-right: 6vw;
}
.leftDiv{
  width: 15vw;
  display: flex;
  flex-direction: column;
}

.filter{
  color : #eee;
  font-size: 20px;
  margin-top: 250px;
}

.filter > ul{
  list-style-type: none;
  display: flex;
  flex-direction: column;
}

.rightDiv{
  width: 70vw;
  overflow: scroll;
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.rightDiv::-webkit-scrollbar {
  display: none;
}

.black-bg,
.white-bg{
  box-sizing: border-box;

}
.black-bg{
  background-color: rgba(0, 0, 0, 0.5);
  position: absolute;
  width: 100vw;
  height: calc(100vh - 70px);
  z-index: 4;
  display: flex;
  justify-content: center;
  align-items: center;
}
.white-bg{
  width: 50%;
  height: 40%;
  background: #414556;
  border-radius: 13px;
  padding: 20px;
  display: flex;
}

.startDatePicker{
  position: relative;
}
.vuecal__weekdays-headings,
.vuecal__all-day {
  padding-right : 0px;
}
.white-bg-right{
  padding: 10px;
  width: 60%;
}
.white-bg-left{
  width: 300px;
  position: relative;
}
.titleInput{
  width: 100%;
}
.contentInput{
  width : 100%;
  background-color : #2C2F3B;
  border : none;
  resize: none;
  height: 100px;
  border-radius: 8px;
  padding: 8px;
  margin: 3px;
  color : #fff;
}
.white-bg-right > input,
.timePicker > input{
  background-color : #2C2F3B;
  border : none;
  margin :3px;
  padding: 8px;
  border-radius: 8px;
  color : #fff;
}
.startDate{
  background-color : #2C2F3B;
  border : none;
  margin :3px;
  padding: 8px;
  border-radius: 8px;
  color : #fff;
  width: 45%;
  margin-right: 6px;
}
.endDate{
  background-color : #2C2F3B;
  border : none;
  margin :5px;
  margin-right: 6px;
  padding: 8px;
  border-radius: 8px;
  color : #fff;
  width: 45%
}
.rightInput{
  display: flex;
  margin-bottom: 10px;
}
.rightInput > label{
  color : #fff;
}
.filterBtn{
  color: white;
  width: 10%;
}
.vue__time-picker{
  width : 49%;
}


#cancelBtn{
  background-color: #FF5C5C;
  color : #eee;
  border : none;
  margin :3px;
  padding: 8px;
  border-radius: 8px;
  width: 40%;
}

#createBtn{
  background-color: #FF8906;
  color : #eee;
  border : none;
  margin :3px;
  padding: 8px;
  border-radius: 8px;
  width: 40%;
}
.startDatePicker,
.endDatePicker{
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.classification-btn{
  margin-bottom: 10px;
}
.finish-wrap{
  display: flex;
  justify-content: space-around;
}

</style>
