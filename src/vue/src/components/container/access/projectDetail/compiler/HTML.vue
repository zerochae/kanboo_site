<template>
  <div class="html-container">
    <EditAndResult/>
  </div>  
</template>

<script>
import EditAndResult from '../../../../component/acess/projectDetail/compiler/html/EditAndResult.vue'

export default {
  name: "htmlCompiler",
  components: {
    EditAndResult
  }
}
</script>

<style scoped>
.html-container {
  width: 85%;
  display: flex;
  justify-content: space-between;
}
</style>