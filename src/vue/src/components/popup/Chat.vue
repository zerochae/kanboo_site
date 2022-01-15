<template>

  <button
      style="
      position: absolute;
      top: 10%;
      right: 40%;
      background: #eee;
      width: 100px;
      height: 50px;
    "
      @click="getAllRoomFindByMemIdx"
  >
    방생성 테스트
  </button>

  <button  style="
      position: absolute;
      top: 20%;
      right: 40%;
      background: #eee;
      width: 100px;
      height: 50px;
    " @click="this.userId = 'yunyun'"
  > 아이디 yunyun </button>
  <button  style="
      position: absolute;
      top: 20%;
      right: 20%;
      background: #eee;
      width: 100px;
      height: 50px;
    "
           @click="this.userId = 'zerozero'"
  > 아이디 zerozero </button>

  <div class="chat-container">
    <div class="chat-box" id="chatRoom">
      <div class="chat-header">
        <i
            v-if="!chatData.isMini"
            @click="setMini()"
            class="fas fa-minus"
        ></i>
        <i v-else @click="setMax()" class="far fa-square"></i>
      </div>
      <div class="chat-content">
        <ul>
          <!-- v-for="(line, index) in chatData.content" -->
          <li
              class="chat-line"
              v-for="(line, index) in s_chatData.content"
              :key="index"
              :class="{ 'chat-myLine': line.id == userId }"
          >
            <ul v-if="line.line" class="date-line">
              <li class="chat-info">
                <span class="date-box">{{line.momentedDate}}</span>
              </li>
            </ul>
            <div class="me-or-other-wrap">
              <!-- 상대방의 채팅에만 사진,닉네임 표시 시작 -->
              <div v-if="userId != line.id" class="chat-userInfo">
                <img :src="require(`@/assets/${line.img}`)" alt="img" />
                {{ line.id }}
              </div>
              <!-- 상대방의 채팅에만 사진,닉네임 표시 끝 -->

              <!-- 상대방의 채팅은 회색 배경 시작 -->
              <div class="chat-info" v-if="line.id != userId">
              <span class="chat-text chat-friend">
                {{ line.text }}
              </span>
                <span class="chat-date">{{ line.date }}</span>
              </div>
              <!-- 상대방의 채팅은 회색 배경 끝 -->

              <!-- 내 채팅은 주황색 배경 시작 -->
              <div class="chat-info" v-else>
                <span class="chat-date">{{ line.date }}</span>
                <span class="chat-text chat-my">
                {{ line.text }}
              </span>
              </div>
              <!-- 내 채팅은 주황색 배경 끝 -->
            </div>
          </li>
        </ul>
      </div>
      <div class="chat-inputBox">
        <input
            :id="`chat-input`"
            @input="setText"
            @keyup.enter="sendMessage()"
            type="text"
        />
        <button @click="sendMessage()">전송</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from 'vuex';
import chatData from "../../assets/chatData.js";
import moment from 'moment'

export default {

  updated() {
    this.focus(this.lastChatRoom);
  },
  mounted() {
    if(this.s_chatData === null || this.s_chatData.content.length === 0)this.callDataOfAllChat()
  },
  computed : {
    ...mapState({
      s_chatData : state => state.socket.s_chatData
    })
  },
  data() {
    return {
      chatData: chatData,
      inputText: "",
      userId: "yunyun",
      img: "con3.jpg",
      lastChatRoom: "",
    }
  },
  methods: {
    ...mapMutations({
      setSendChatting : 'socket/setSendChatting',
      increaseChatCnt : 'socket/increaseChatCnt',
      callMessageSocket : 'socket/sendMessage',
      setIdValue : 'socket/setIdValue',
    }),
    ...mapActions({
      callDataOfAllChat : 'socket/callDataOfAllChat',
      getAllRoomFindByMemIdx : 'socket/getAllRoomFindByMemIdx',
    }),
    setText(e) {
      this.inputText = e.target.value;
    },
    sendMessage() {
      let date = moment().format('HH:mm')
      if (this.inputText !== "") {
        const arr = {
          id : this.userId,
          text : this.inputText,
          date : date,
          img : this.img
        }
        this.callMessageSocket(arr)
      }
      document.querySelector(`#chat-input`).value = "";
      this.inputText = "";
    },
    focus() {
      let chatRoom = document.querySelector("#chatRoom>.chat-content>ul");
      chatRoom.lastElementChild.scrollIntoView(false);
    },
    setMini() {
      let chatRoom = document.querySelector(`#chatRoom`);
      chatRoom.className = "chat-box chat-mini";
      this.chatData.isMini = true;
      console.log(this.chatData);
    },
    setMax() {
      let chatRoom = document.querySelector(`#chatRoom`);
      chatRoom.className = "chat-box";
      this.chatData.isMini = false;
    },
    // 채팅 메시지 받기 테스트
    chatTest() {
      this.chatData.content.push({
        id: "kade",
        text: "머하냐~",
        // id : this.$store.state.socket.receivedChat.id,
        // text : this.$store.state.socket.receivedChat.text,
        img: "con1.jpg",
        date: this.$store.state.socket.receivedChat.date,
      });
      console.log('chatTest =>> ', this.chatData.content)
    },
  },
  watch : {
    '$store.state.socket.receiveChatCnt'(){
      this.focus()
    },
  },
};
</script>

<style scoped>
.chat-container {
  position: fixed;
  right: 0;
  bottom: 0;
  display: flex;
  overflow: hidden;
  z-index: 1;
}
.chat-box {
  width: 20vw;
  min-width: 260px;
  height: 40vh;
  background: #2c2f3b;
  border-radius: 5px;
  position: relative;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
  margin: 5px;
  overflow: auto;
  display: flex;
}
.chat-header {
  position: absolute;
  top: 0;
  height: 18px;
  background: #414556;
  text-align: right;
  width: 100%;
  box-shadow: 3px 6px 6px rgba(255, 255, 255, 0.2) inset;
  z-index: 5;
}

.chat-header i {
  font-size: 18px;
  margin-right: 8px;
  color : white;
}

.chat-content {
  padding: 15px;
  font-size: 12px;
  overflow: auto;
  width: 100%;
}

.chat-content ul {
  margin-top: 6px;
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
  color: white;
}

.chat-line {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 6px;
}

.chat-userInfo {
  display: flex;
  flex-direction: column;
  margin: 5px;
  align-items: center;
}

.chat-userInfo img {
  border-radius: 50%;
  width: 20px;
  margin-bottom: 7px;
}

.chat-info {
  display: flex;
}

.chat-text {
  margin-left: 10px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  padding: 15px;
  position: relative;
  word-break: break-word;
  max-width: 75%;
}
.chat-friend::before {
  background: linear-gradient(
      -135deg,
      #414556 0%,
      #414556 50%,
      transparent 50%,
      transparent
  );
  content: "";
  position: absolute;
  top: 10px;
  left: -8px;
  width: 12px;
  height: 12px;
  z-index: 1;
}

.chat-friend {
  background: #414556;
  -webkit-filter: drop-shadow(0px 2px 2px rgba(10, 10, 10, 0.8));
}

.chat-myLine {
  justify-content: right;
  align-items: flex-end;
}

.chat-my::before {
  background: linear-gradient(
      135deg,
      #ff8906 0%,
      #ff8906 50%,
      transparent 50%,
      transparent
  );
  content: "";
  position: absolute;
  top: 10px;
  right: -8px;
  width: 12px;
  height: 12px;
  z-index: 1;
}

.chat-my {
  background: #ff8906;
  -webkit-filter: drop-shadow(0px 2px 2px rgba(10, 10, 10, 0.8));
}

.chat-date {
  margin-top: auto;
  margin-left: 8px;
  margin-bottom: 6px;
}

.chat-inputBox {
  height: 30px;
  width: 100%;
  position: absolute;
  background: #414556;
  bottom: 0;
  display: flex;
  align-items: center;
  box-shadow: 3px -3px 6px rgba(255, 255, 255, 0.2) inset;
}
.chat-inputBox input {
  margin-left: 10px;
  background: none;
  color: white;
  border: none;
  outline: none;
  width: 100%;
}

.chat-inputBox button {
  background: #ff8906;
  border-radius: 10px;
  font-size: 12px;
  width: 50px;
  height: 20px;
  color: white;
  -webkit-filter: drop-shadow(0px 2px 2px rgba(0, 0, 0, 0.1));
  margin-right: 10px;
}

::-webkit-scrollbar {
  width: 0px;
}

.chat-mini {
  overflow: hidden;
  width: 100px;
  height: 18px;
  bottom: 0;
  position: relative;
  align-self: flex-end;
}

.chat-mini i {
  font-size: 12px;
}

.me-or-other-wrap {
  display: flex;
}

.date-line {
  align-self: center;
}

.date-box {
  background: #ff8906;
  color: #fff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 12px;
  line-height: 1.25;
}
@media (max-width:768px) {
  span{
    font-size: 10px;
  }
}

</style>