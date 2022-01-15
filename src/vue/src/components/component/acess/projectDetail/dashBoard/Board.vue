<template>
  <div class="container">
    <h2>Board</h2>
    <ul class="board-list">
      <li v-for="(item, index) in boardList" 
          :key="index">
      <a :href="item.idx">{{item.content}}</a>
      </li>
    </ul>
  </div>  
</template>

<script>
export default {
  name: "Board",
  data() {
    return {
      boardList: [
        {
          idx: 1,
          content: "오늘 날씨가 좋구려..."
        },
        {
          idx: 1,
          content: "오늘 날씨가 좋구려..."
        },
        {
          idx: 1,
          content: "오늘 날씨가 좋구려..."
        },
        {
          idx: 1,
          content: "오늘 날씨가 좋구려..."
        },
        {
          idx: 1,
          content: "오늘 날씨가 좋구려..."
        },
        {
          idx: 1,
          content: "오늘 날씨가 좋구려..."
        },
      ]
    }
  }
}
</script>

<style scoped>
.container {
  border-radius: 5px;
  background: #2C2F3B;
  height: 240px;
  padding: 15px 20px;
  color: #ffffff;
}

h2 {
  font-size: 32px;
  margin-bottom: 10px;
}

.board-list {
  padding-left: 10px;
  font-size: 18px;
  color: #999999;
}

.board-list li {
  margin-bottom: 10px;
}

.board-list li:last-child {
  margin: 0;
}

.board-list a {
  transition: color .2s ease-in;
}

.board-list a:hover {
  color: #ffffff;
}

</style>