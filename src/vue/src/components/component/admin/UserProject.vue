<template>
  <div>
    <div class="header">
      <span class="title">User Project</span>
      <div>
        <select class="selectBox" @change="setSelect">
          <option selected value="All">전체</option>
          <option value="prjctNm">프로젝트이름</option>
          <option value="memNick">팀장닉네임</option>
        </select>
        <input
            class="search"
            type="text"
            placeholder="🔎"
            @input="setKey"
            @keyup="search"
        />
      </div>
    </div>

    <ul class="body" @scroll="getMore">
      <li v-for="data in projectData" :key="data">
        <span> {{ data.name }} &nbsp; # {{ data.idx }} </span>
        <span> 팀장 : &nbsp; {{ data.memId }} </span>
        <span>
          상태 :
          <select @change="(e) => modifyState(e, data.idx)" class="selectBox">
            <option :selected="data.isCom === 'n'" value="진행">진행</option>
            <option :selected="data.isCom === 'y'" value="완료">완료</option>
            <option :selected="data.isDel === 'y'" value="삭제">삭제</option>
          </select>
        </span>
        <span> 기간 : &nbsp; {{ data.start }} ~ {{ data.end }} </span>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";
export default {
  computed: mapState({
    projectData: (state) => state.userProject.projectData,
    articleOnView: (state) => state.userProject.articleOnView,
    maxIndex: (state) => state.userProject.maxIndex,
    isAxiosRunning: (state) => state.userProject.isAxiosRunning,
  }),
  data() {
    return {
      selected: "",
      keyWord: "",
    };
  },
  created() {
    this.setProjectList();
    this.getMaxProjectIndex();
  },
  methods: {
    ...mapMutations({
      update: "userProject/update",
      setAxiosState: "userProject/setAxiosState",
      setSelected: "userProject/setSelected",
      setKeyWord: "userProject/setKeyWord",
      resetData: "userProject/resetData",
    }),
    ...mapActions({
      setProjectList: "userProject/setProjectList",
      getMaxProjectIndex: "userProject/getMaxProjectIndex",
      getMoreList: "userProject/getMoreList",
    }),
    modifyState(e, idx) {
      let payload = [];
      payload.push(idx, e.target.value);
      this.update(payload);
    },
    getMore(e) {
      if (this.maxIndex == this.articleOnView) {
        return;
      }

      const scrollHeight = e.target.scrollHeight;
      const scrollTop = e.target.scrollTop;
      const clientHeight = e.target.clientHeight;

      if (scrollTop + clientHeight >= scrollHeight) {
        console.log("getMore");
        this.getMoreList();
      }
    },
    setSelect(e) {
      this.setSelected(e.target.value);
      this.search()
    },
    setKey(e) {
      this.setKeyWord(e.target.value);
    },
    search() {
      this.resetData();

      console.log("da")

      this.setProjectList();
      this.getMaxProjectIndex();
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
  border-radius: 8px;
  color: #fff;
  padding: 6px;
}

.body {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: scroll;
  height: 90%;
}

.body li {
  margin-bottom: 22px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background: #2c2f3b;
  height: 30px;
  width: 90%;
  padding: 15px;
  color: #fff;
  border-radius: 6px;
  animation: fade 0.3s linear;
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

.body li:last-child {
  margin-bottom: 35px;
}

::-webkit-scrollbar {
  width: 0;
}

@keyframes fade {
  0%{
    opacity: 0;
  }
  100%{
    opacity: 1;
  }
}

@media (max-width: 1480px) {
  .body li {
    display: grid;
    grid-template-columns: 1fr 1fr;
    row-gap: 15px;
    padding: 15px;
    height: fit-content;
  }
}

@media (max-width: 1268px) {
  .body {
    height: 50vh;
    overflow: scroll;
  }
}

@media (max-width: 768px) {
  .header {
    display: flex;
    flex-direction: column;
  }

  .header .search {
    margin-top: 25px;
    height: 30px;
    width: 50%;
  }

  .body {
    height: 50vh;
    overflow: scroll;
  }

  .body li {
    display: grid;
    grid-template-columns: 1fr 2fr;
    text-align: center;
    width: 100%;
    font-size: 14px;
  }
}

@media (max-width: 535px) {
  .header .search {
    margin-top: 25px;
    height: 30px;
    width: 100%;
  }

  .body {
    overflow: scroll;
    height: 50vh;
  }

  .body li {
    display: flex;
    flex-direction: column;
    width: 100%;
    font-size: 12px;
    align-items: flex-start;
  }

  .body li span select {
    font-size: 12px;
  }
}
</style>