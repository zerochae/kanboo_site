<template>
  <div class="container">
    <Chart :maxHeight="maxHeight"/>
  </div>
</template>

<script>
import Chart from '@/components/component/acess/projectDetail/ganttChart/Chart'

export default {
  name: "gantt",
  data() {
    return {
      ganttData: {},
      maxHeight: '100%'
    }
  },
  components: {
    Chart
  },
  methods: {

  },
  mounted() {

  }
}
</script>

<style scoped>
.container {
  width: 70vw;
}
</style>