<template>
  <div class="container">
    <h2>Git Commit</h2>
    <ul class="git-list">
      <li v-for="item in gitList" :key="item">
        <a :href="item.link">- {{item.title}}</a>
      </li>
    </ul>
  </div>  
</template>

<script>
export default {
  name: "Commit",
  data() {
    return {
      gitList: [
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
        {
          link: "https://www.github.com",
          title: "git-last-commit"
        },
      ]
    }
  }
}
</script>

<style scoped>
.container {
  height: 340px;
  width: 45%;
  border-radius: 5px;
  background: #2C2F3B;
  padding: 10px;
  color: #ffffff;
}

h2 {
  font-size: 32px;
  margin-bottom: 20px;
}

.git-list {
  padding: 0 20px;
  font-size: 28px;
  height: calc(100% - 52px);
}

.git-list li {
  margin-bottom: 10px;
}

.git-list li:last-child {
  margin-bottom: 0;
}
</style>