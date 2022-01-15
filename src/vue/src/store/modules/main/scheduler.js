import moment from 'moment' // eslint-disable-line no-unused-vars

const scheduler = {
  namespaced: true,
  state() {
    return {
      selectedDate : '',
      isToggle : false,
      showAllDayEvents: 0,
      shortEventsOnMonthView: false,
      showEventCreationDialog: false,
      changeTheme : false,
      changeLang : false,
      isModal : false,
      callAddFunction : false,

      fakedata :[
        {
          id : '16',
          start: '2021-12-09 09:15',
          end: '2021-12-09 11:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'common',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '15',
          start: '2021-12-09 11:15',
          end: '2021-12-09 13:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'individual',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : true,
          isgantt : false,
        },
        {
          id : '14',
          start: '2021-12-08 08:15',
          end: '2021-12-08 11:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'notice',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '13',
          start: '2021-12-09 11:15',
          end: '2021-12-09 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'emergency',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '12',
          start: '2021-12-09 11:15',
          end: '2021-12-09 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'vacation',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '11',
          start: '2021-12-09 11:15',
          end: '2021-12-09 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'note',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '10',
          start: '2021-12-09 11:15',
          end: '2021-12-09 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'emergency',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '9',
          start: '2021-12-08 11:15',
          end: '2021-12-08 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'common',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '1',
          start: '2021-12-07 11:15',
          end: '2021-12-07 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'individual',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '2',
          start: '2021-12-10 11:15',
          end: '2021-12-10 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'emergency',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '3',
          start: '2021-12-11 11:15',
          end: '2021-12-11 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'notice',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '4',
          start: '2021-12-12 11:15',
          end: '2021-12-12 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'note',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '5',
          start: '2021-12-13 11:15',
          end: '2021-12-13 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'notice',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '6',
          start: '2021-12-14 11:15',
          end: '2021-12-14 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'note',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
        {
          id : '7',
          start: '2021-12-15 11:15',
          end: '2021-12-15 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'common',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : true,
          isgantt : false,
        },
        {
          id : '8',
          start: '2021-12-16 11:15',
          end: '2021-12-16 15:15',
          title: '테스트일정 제목',
          content: '테스트일정 내용',
          class: 'emergency',
          deletable: true,
          resizable: true,
          draggable: true,
          allDay : false,
          isgantt : false,
        },
      ],
      copiedData : [{}],
      data : [],
      secondData :[],
      filteredData : [],
      // 원본데이터는  배열1에 저장 렌더는 배열2에 렌더링 filter메소드는 배열 2를 가지고 렌더링,
      //  전체를 클릭시 배열 1로 다시 배열2에 렌더링 도중에 crud 시 전체로 다시 필터 초기화
      ganttData : [
        {
          // 간트 일정 별 색상을 따로 불러올 때 지정함 0~29% gray, 30%~59% blue, 60%~100% green
          class1 : 'gantt-gray',
          class2 : 'gantt-blue',
          class3 : 'gantt-green',
        }
      ],
    }
  },
  mutations: {
    showData(state){
      console.log(state.addEventData)
    },
    setSelectDate(state, event){
      state.selectedDate = event
    },
    setModal(state){
      state.isModal = true
    },
    closeModal(state, e){
      for(let i in e.target.classList){
        if(e.target.classList[i] === 'black-bg' || e.target.classList[i] === 'closeModalBtn'){
          state.isModal = false
        }else{
          return
        }
      }
    },
    setCallAddFunction(state){
      state.callAddFunction = !state.callAddFunction
      if(state.callAddFunction) {
        state.isModal = false
      }
    },
    setFilterValue(state, e){
      this.commit('scheduler/copyDataFunction')
      state.filterValue = e
      const copy = [...state.secondData]

      if(state.showAllDayEvents === 2){
        for(let i = 0; i < copy.length; i++){
          if(copy[i].allDay === true){
            copy.splice(i, 1)
          }
        }
      }

      if(state.filterValue !== 'all'){
        let filtered = copy.filter( (v)=>(v.class === state.filterValue) )
        state.data = filtered
        state.filteredData = filtered
      }else if(state.filterValue === 'all'){
        state.data = state.secondData
      }
    },
    setModalTrue(state){
      state.isModal = true
    },
    setModalFalse(state){
      state.isModal = false
    },
    copyDataFunction(state){
      state.copiedData = [...state.secondData]
    },
    toggleAllDayContent(state){
      const copy = [...state.data]
      if(!state.isToggle){
        for(let i = 0; i < copy.length; i++){
          if(copy[i].allDay === true){
            copy.splice(i, 1)
          }
        }
        this.commit('scheduler/copyDataFunction')
        state.data = [...copy]
        state.showAllDayEvents = 2
      }else if(state.isToggle){
        state.data = state.filteredData
        state.showAllDayEvents = 0
      }
      state.isToggle = !state.isToggle
    },

    setData(state, arr){
      state.data = arr
    },
    pushData(state, arr){
      state.data.push(arr)
    },
    setSecondData(state, arr){
      state.secondData = arr
    },
    pushSecondData(state, arr){
      state.secondData.push(arr)
    },
    setDataToSecondData(state){
      const copy = [...state.secondData]

      if(state.showAllDayEvents === 2){
        for(let i = 0; i < copy.length; i++){
          if(copy[i].allDay === true){
            copy.splice(i, 1)
          }
        }
      }
      state.data = copy
    },

  },
}

export default scheduler