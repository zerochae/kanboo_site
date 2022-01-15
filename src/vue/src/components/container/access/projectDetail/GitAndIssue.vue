<template>
  <div style="height: calc(100vh - 70px)">
    <div class="gitContainer">
      <div class="gitleftDiv">
        <GitRepository class="git"/>
        <Issue class="issue"/>
      </div>
      <div class="gitrightDiv">
        <GitCode/>
      </div>
    </div>
  </div>
</template>

<script>
import GitRepository from '@/components/component/acess/projectDetail/gitAndIssue/Git.vue'
import Issue from '@/components/component/acess/projectDetail/gitAndIssue/Issue.vue'
import GitCode from '@/components/component/acess/projectDetail/gitAndIssue/GitCode.vue'

export default {
  components:{
    GitRepository,
    Issue,
    GitCode,
  },
}
</script>

<style scoped>
.gitContainer{
  padding: 10px 40px;
  display: flex;
  height: calc(100vh - 70px);
  justify-content: space-between;
}
.gitleftDiv{
  width: 45%;
}
.gitrightDiv{
  width: 50%;
  height: 90vh;
}

h1{
  color: #eee;
}

</style>