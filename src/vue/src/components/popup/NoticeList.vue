<template>
  <ul class="notice-container">
    <li class="del-btn">
      <button @click="del" type="button">delete all</button>
    </li>
    <li v-for="item in noticeList" 
        :key="item"
        class="notice-detail">{{item.content}}</li>
  </ul>
</template>

<script>
export default {
  name: "noticeList",
  data() {
    return {
      noticeList: [
        {
          idx: 1,
          content: "hello"
        },
        {
          idx: 1,
          content: "hello"
        },
        {
          idx: 1,
          content: "hello"
        },
        {
          idx: 1,
          content: "hello"
        }
      ]
    }
  },
  methods: {
    del() {
      this.noticeList = []
    }
  }
}
</script>

<style scoped>
.notice-container {
  background: #16161A;
  border: 1px solid #999;
  position: absolute;
  top: 70px;
  right: 0;
  width: 20vw;
  max-height: 40vh;
  overflow: scroll;
  border-radius: 10px;
  z-index: 10;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.del-btn {
  text-align: right;
  margin-bottom: 10px;
}

button {
  color: #999;
}

button:hover {
  color: #fff;
}

.notice-detail {
  line-height: 1.25;
}
</style>