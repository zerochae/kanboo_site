<template>
  <div id="app">
    파랑 팝업하기 => <input @click="alarm('#6464CD')" type="button" value="blue">
    빨강 팝업하기 => <input @click="alarm('#FFB900')" type="button" value="orange">
    초록 팝업하기 => <input @click="alarm('#9EF048')" type="button" value="green">
    유저이름 : <input v-model="id" type="text">
    내용 : <input v-model="text" type="text" @keyup="middleRangeText">
    <hr>
    <div v-for="(item, idx) in recvList" :key="idx" id="chatBox">
      <h3 v-if="item.userName !==null && item.content !== null">
        {{item.userName}} : {{item.content}}
      </h3>
    </div>
    <h3 v-if="this.alarmState !== null && this.alarmState !== ''">
      {{this.alarmState}}
    </h3>
    <hr>
    <input type="button" value="disconnect" @click="disConnect">
    <input type="button" value="connect" @click="connect">
    <hr>
    <textarea name="" class="txtArea" cols="80" rows="30"
              v-model="this.c_textArea_text" @keyup="this.sendTextArea(this.c_textArea_text)"></textarea>
  </div>
</template>

<script>
//stompClient는 내장된 변수
// import Stomp from 'webstomp-client'
// import SockJS from 'sockjs-client'
import {mapActions, mapMutations} from 'vuex'
// const moment = require("moment");
// const today = moment();

export default {
  name: 'ChatAppOrigin',
  components: {
  },
  data() {
    return {
      id : "",
      text: "",
      recvList: [],
      alarmState : '',
      c_textArea_text: '',
    }
  },
  created() {
    this.connect()
  },
  methods: {
    ...mapMutations({
      setAlarmColor : 'socket/setAlarmColor',
      setSendChatting : 'socket/setSendChatting',
      increaseAlarmCnt : 'socket/increaseAlarmCnt',
      serReceivedChatting : 'socket/serReceivedChatting',
      increaseReceiveChatCnt : 'socket/increaseReceiveChatCnt',
      setTextArea_Text : 'socket/setTextArea_Text',
      alarm : 'socket/alarm',
      sendMessage : 'socket/sendMessage',
      send : 'socket/send',
      sendTextArea : 'socket/sendTextArea',
    }),

    ...mapActions({
      connect : 'socket/connect',
    }),

    middleRangeText(e){
      // console.log(e)
      if(e.keyCode === 13) this.sendMessage({id : this.id, text : this.text})
    },
    renderToTextArea(){
      // console.log('renderToTextArea',this.$store.state.socket.textArea_text)
      this.c_textArea_text = this.$store.state.socket.textArea_text
    },
    // alarm(color) {
    //   if(color !== null){
    //     if(this.stompClient && this.stompClient.connected) {
    //       const msg = {
    //         alarm: color,
    //         // member: sessionStorage.getItem("token")
    //       }
    //       this.stompClient.send("/alarm", JSON.stringify(msg), {})
    //     } // stomp if
    //   } // color if
    // },

    // sendTextArea(){
    //   console.log('입력중')
    //   if(this.stompClient && this.stompClient.connected){
    //     let arr_textAreaText = {
    //       textAreaText : '',
    //     }
    //     arr_textAreaText.textAreaText = this.textArea_text
    //     this.stompClient.send('/receive', JSON.stringify(arr_textAreaText), {})
    //     // this.textArea_text = ''
    //   }
    //     // this.textArea_text = ''
    // },

    // sendMessage(e) {
    //   if(e.keyCode === 13 && this.id.trim() !== '' && this.text.trim() !== '') {
    //     this.send()
    //     this.text = ''
    //   }
    // },

    // send() {
    //   // let date = today.format("HH:MM");
    //   if(this.stompClient && this.stompClient.connected) {
    //     const msg = {
    //       id: this.$store.state.socket.sendChat.id,
    //       text: this.$store.state.socket.sendChat.text,
    //       date : this.$store.state.socket.sendChat.date,
    //     }
    //     this.stompClient.send("/receive", JSON.stringify(msg), {})
    //   }
    // },

    // connect() {

    //   const serverURL = "http://192.168.0.8:8099/"
    //   let socket = new SockJS(serverURL)
    //   this.stompClient = Stomp.over(socket)
    //   // SockJs와 stomp.js를 사용하여 SockJS 서버가 연결을 기다리는 곳에 연결한다.
    //   this.stompClient.connect(
    //       {},
    //       frame => {
    //         this.connected = true
    //         console.log(frame)
    //         this.stompClient.subscribe("/send", res => {
    //           console.log("소켓에서 수신한 내용 =>>>>", JSON.parse(res.body))
    //           let arr = JSON.parse(res.body)

    //           // if(arr.member === sessionStorage.getItem("token") || this.$store.state.project.projectIdx !== sessionStorage.getItem("token").projectIdx) {
    //           //   return
    //           // }
    //           if(arr.alarm !== null && arr.alarm !== '' && arr.textAreaText === null){
    //             this.alarmState = arr.alarm
    //             this.setAlarmColor(arr.alarm)
    //             // this.$store.state.alarmAndChat.alarmCnt++
    //             this.increaseAlarmCnt()
    //           }else if(arr.userName !== null && arr.userName !== '' && arr.message !== null && arr.message !== '' && arr.textAreaText === null){
    //             this.recvList.push(JSON.parse(res.body))
    //             this.serReceivedChatting(arr)
    //             // increase말고 내용( 배열 변수 )을 바꿧을 때 watch 발동이 될까?
    //             this.increaseReceiveChatCnt()
    //           }else if(arr.textAreaText != null){
    //             // console.log('=> ', arr.textAreaText)
    //             this.textArea_text = arr.textAreaText
    //             // this.setTextArea_Text(arr.textAreaText)
    //           }
    //         })
    //       },
    //       error => {
    //         console.log(error)
    //         this.connected = false
    //       }
    //   )

    // },
    disConnect(){
      if(this.stompClient != null){
        let chatBox = document.querySelector('#chatBox')
        console.log(chatBox)
        // chatBox.innerHTML = ''
        this.stompClient.disconnect()
      }
      console.log('disConnected')
    },
  },// methods
  watch : {
    alarmState(){
      console.log('socket.vue에서의 watch', this.alarmState)
    },
    // 메세지 송신 이벤트 발생시키기
    '$store.state.socket.chatCnt'() {
      this.send()
    },
    '$store.state.socket.textAreaCnt'() {
      this.renderToTextArea()
    }
  },
}
</script>

<style scoped>

</style>