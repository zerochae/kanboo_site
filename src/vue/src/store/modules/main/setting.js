const setting = {
  namespaced: true,
  state: {

    clickState  : false,
    projectName : 'Test_Project',
    roleList : [// 여기에 ajax로 요청한 데이터 들어와야함.
      'PM','PL','DA','TA','AA','UA','BA','EA','SA'
    ],

    projectDate : {

      startDate : '2021/12/08',
      endDate : '2021/12/25'

    },

    userInputDate : {

      startDate : '',
      endDate : ''
    },


    roleMatch : [

    ],

    searchMemberList : [
      {
        idx : '1',
        nickname : 'aaaa',
        kTag : '#sear12',
        img : 'https://placeimg.com/640/480/arch'
      },
      {
        idx : "2",
        nickname : "bbbb",
        kTag : "#123ab",
        img : "https://placeimg.com/640/480/arch"
      },
      {
        idx : "3",
        nickname : "cccc",
        kTag : "#123ab",
        img : "https://placeimg.com/640/480/arch"
      },
    ],

    selectMemberList : [
      {
        idx : '4',
        nickname : 'dddd',
        kTag : '#sele12',
        img : 'https://placeimg.com/640/480/arch',
        position : ""
      },
      {
        idx : "5",
        nickname : "eeee",
        kTag : "#123ab",
        img : /*'@/testData/0.png'*/"../../../../../testData/0.png",
        position : ""
      },
      {
        idx : "6",
        nickname : "ffff",
        kTag : "#123ab",
        img : "https://placeimg.com/640/480/arch",
        position : ""
      },
      {
        idx : "7",
        nickname : "gggg",
        kTag : "#123ab",
        img : "https://placeimg.com/640/480/arch",
        position : ""
      },
    ],

    projectMemberList : [ // 여기에 ajax로 요청한 데이터 들어와야함.
      {
        idx : '1517',
        projectIdx : 'p1234',
        member : {
          idx : '1517',
          id  : 'lim1517',
          nickname : 'knightlim1',
          phoneNumber : '010-1234-1234',
          token : 'dfggkndfhcvb',
          kTag : '#1',
          img : 'https://placeimg.com/640/480/arch',
          authority : 'user',
          password : 'asdf'
        },
        role : ['PM','DA']
      },
      {
        idx : '1517',
        projectIdx : 'p1234',
        member : {
          idx : '1517',
          id  : 'lim1517',
          nickname : 'knightlim2',
          phoneNumber : '010-1234-1234',
          token : 'dfggkndfhcvb',
          kTag : '#2',
          img : 'https://placeimg.com/640/480/arch',
          authority : 'user',
          password : 'asdf'
        },
        role : ['SA','EA']
      },
      {
        idx : '1517',
        projectIdx : 'p1234',
        member : {
          idx : '1517',
          id  : 'lim1517',
          nickname : 'knightlim3',
          phoneNumber : '010-1234-1234',
          token : 'dfggkndfhcvb',
          kTag : '#3',
          img : 'https://placeimg.com/640/480/arch',
          authority : 'user',
          password : 'asdf'
        },
        role : ['BA']
      },
      {
        idx : '1517',
        projectIdx : 'p1234',
        member : {
          idx : '1517',
          id  : 'lim1517',
          nickname : 'knightlim7',
          phoneNumber : '010-1234-1234',
          token : 'dfggkndfhcvb',
          kTag : '#4',
          img : 'https://placeimg.com/640/480/arch',
          authority : 'user',
          password : 'asdf'
        },
        role : ['TA','UA']
      },
      {
        idx : '1517',
        projectIdx : 'p1234',
        member : {
          idx : '1517',
          id  : 'lim1517',
          nickname : 'knightlim',
          phoneNumber : '010-1234-1234',
          token : 'dfggkndfhcvb',
          kTag : '#5',
          img : 'https://placeimg.com/640/480/arch',
          authority : 'user',
          password : 'asdf'
        },
        role : ['TA','DA']
      },

    ]


  },
  mutations: {
    changeClickState(state){
      if(state.clickState == false){
        state.clickState = true;
      } else{
        state.clickState = false;
      }
    },


  },
  actions: {

  }
}

export default setting