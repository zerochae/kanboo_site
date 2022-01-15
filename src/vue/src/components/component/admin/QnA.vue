<template>
  <div>
    <div class="header">
      <span class="title">QnA</span>
      <div>
        <select class="selectBox" @change="setSelect">
          <option selected value="All">전체</option>
          <option value="boardCn">내용</option>
          <option value="memNick">닉네임</option>
        </select>
        <input
            class="search"
            type="text"
            placeholder="🔎"
            @input="setKey"
            @keyup.enter="search"
        />
      </div>
    </div>
    <div class="body" @scroll="getMore">
      <div class="card" v-for="(data, index) in qnaData" :key="index">
        <p class="card-header">
          <span class="board-writer">{{ data.memNick }}</span>
          <span class="board-date">{{ getDate(data.date) }}</span>
        </p>
        <p class="card-body">{{ data.content }}</p>
        <input
            type="text"
            @keyup.enter="addReply(index)"
            @input="setText"
            placeholder="댓글을 입력하세요"
        />
        <p class="card-reply" v-for="reply in data.replies" :key="reply">
          <span class="reply-writer">
            {{ reply.memNick }}
            <span class="reply-date">{{ getDate(reply.date) }}</span>
          </span>
          <span class="reply-content"> {{ reply.content }} </span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      inputText: "",
    };
  },
  computed: {
    ...mapState({
      qnaData: (state) => state.adminQnA.qnaData,
      articleOnView: (state) => state.adminQnA.articleOnView,
      maxIndex: (state) => state.adminQnA.maxIndex,
      isAxiosRunning: (state) => state.adminQnA.isAxiosRunning,
    }),
  },
  created() {
    this.setQnaList();
    this.getMaxBoardIndex();
  },
  methods: {
    ...mapMutations({
      insert: "adminQnA/insert",
      setAxiosState: "adminQnA/setAxiosState",
      setSelected: "adminQnA/setSelected",
      setKeyWord: "adminQnA/setKeyWord",
      resetData: "adminQnA/resetData",
    }),
    ...mapActions({
      setQnaList: "adminQnA/setQnaList",
      getMoreList: "adminQnA/getMoreList",
      getMaxBoardIndex: "adminQnA/getMaxBoardIndex",
    }),
    setText(e) {
      this.inputText = e.target.value;
    },
    getDate(date) {
      let temp = moment(date, "YYYY-MM-DD HH:mm:ss");
      return `${temp.format("MM")}월 ${temp.format("DD")}일 ${temp.format(
          "HH"
      )}:${temp.format("mm")}`;
    },
    addReply(index) {
      let today = moment().format("YYYY-MM-DD HH:mm:ss");

      let payload = [];
      let data = {
        memNick: "admin",
        boardIdx: this.qnaData[index].boardIdx,
        content: this.inputText,
        date: today,
      };
      payload.push(index, data);
      this.insert(payload);

      this.inputText = "";
      document.querySelectorAll(".body input")[index].value = "";
    },
    getMore(e) {
      console.log(this.maxIndex)
      console.log(this.articleOnView)
      if (this.maxIndex == this.articleOnView) {
        return;
      }


      const scrollHeight = e.target.scrollHeight;
      const scrollTop = e.target.scrollTop;
      const clientHeight = e.target.clientHeight;

      if (scrollTop + clientHeight >= scrollHeight - 30) {
        if (!this.isAxiosRunning) {
          window.requestAnimationFrame(() => {
            console.log("getMore");
            this.getMoreList();
          });
          this.setAxiosState();
        }
      }
    },
    setSelect(e) {
      this.setSelected(e.target.value);
      this.search();
    },
    setKey(e) {
      this.setKeyWord(e.target.value);
    },
    search() {
      this.resetData();

      this.setQnaList();
      this.getMaxBoardIndex();
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
}

.title {
  font-size: 28px;
  color: #fff;
}

.search {
  background: #414556;
  width: 150px;
  border: none;
  outline: none;
  border-radius: 6px;
  color: #fff;
  padding: 6px;
}

.selectBox {
  border: none;
  background: none;
  color: #fff;
  outline: none;
  width: fit-content;
  text-align: center;
}

.selectBox option {
  background: #2c2f3b;
  color: #fff;
}

.body {
  width: 90%;
  margin: auto;
  height: 97%;
  overflow: scroll;
}

.board-writer {
  font-size: 22px;
}

.board-date {
  font-size: 12px;
}

.card {
  width: 100%;
  background: #2c2f3b;
  margin-bottom: 20px;
  padding: 20px;
  color: #fff;
  border-radius: 8px;
  animation: fade 0.3s linear;
}

.card-body {
  font-size: 22px;
}

.card > * {
  margin-bottom: 15px;
  padding: 6px;
  border-radius: 8px;
}

.card-header {
  font-size: 20px;
}

.card input {
  background: #414556;
  border: none;
  outline: none;
  height: 30px;
  padding: 10px;
  width: 100%;
  color: #fff;
}

.card-reply {
  background: #414556;
  width: fit-content;
  display: flex;
  flex-direction: column;
  padding: 14px;
}

.reply-writer {
  font-size: 18px;
}

.reply-date {
  font-size: 12px;
}

.reply-content {
  font-size: 18px;
  margin-top: 12px;
}

::-webkit-scrollbar {
  width: 0;
}

@keyframes fade {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@media (max-width: 1268px) {
  .body {
    overflow: scroll;
    height: 50vh;
  }
}

@media (max-width: 768px) {
  .body {
    overflow: scroll;
    height: 50vh;
    width: 100%;
  }
}

@media (max-width: 535px) {
  .header {
    display: flex;
    flex-direction: column;
  }

  .header .search {
    margin-top: 25px;
    height: 30px;
    width: 100%;
  }

  .body {
    overflow: scroll;
    height: 50vh;
    width: 100%;
  }

  .card-header {
    display: flex;
    flex-direction: column;
  }

  .board-writer {
    font-size: 14px;
    margin-bottom: 10px;
  }

  .board-date {
    font-size: 10px;
  }

  .card p {
    font-size: 12px;
  }

  .card-reply span {
    font-size: 12px;
  }
}
</style>