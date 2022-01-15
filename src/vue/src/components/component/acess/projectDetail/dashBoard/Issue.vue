<template>
  <div class="container">
    <h2>Issue</h2>
    <ul class="issue-list">
      <li v-for="(item, index) in issueList" 
          :key="index">
      <a :href="item.idx">{{item.title}}</a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Issue",
  data() {
    return {
      issueList: [
        {
          idx: 1,
          title: "HomeController 버그",
        },
        {
          idx: 2,
          title: "Entity 설계 수정",
        },
        {
          idx: 3,
          title: "집 가고 싶다",
        },
        {
          idx: 4,
          title: "Hello world!!",
        },
        {
          idx: 5,
          title: "Hello java, jpa, springBoot"
        },
        {
          idx: 6,
          title: "Hello java, jpa, springBoot"
        },
      ]
    }
  },

}
</script>

<style scoped>
.container {
  width: 20vw;
  height: 240px;
  background: #2C2F3B;
  padding: 15px 20px;
  border-radius: 5px;
  color: #ffffff;
}

h2 {
  font-size: 32px;
  margin-bottom: 10px;
}

.issue-list {
  padding-left: 10px;
  font-size: 18px;
  color: #999999;
}

.issue-list li {
  margin-bottom: 10px;
}

.issue-list li:last-child {
  margin: 0;
}

.issue-list a {
  transition: color .2s ease-in;
}

.issue-list a:hover {
  color: #ffffff;
}
</style>