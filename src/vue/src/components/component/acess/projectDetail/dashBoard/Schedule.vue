<template>
  <div class="container">
    <vue-cal
        small
        locale='ko'
        hide-view-selector

        :time-from="0 * 60"
        :time-to="24 * 60"
        :transitions="false"
        active-view="week"
        :disable-views="['years', 'year', 'month', 'day']"
        resize-x
        :events=$store.state.scheduler.dasdfasda
        class="vuecal--dark-theme vuecal--full-height-delete"
        :drag-to-create-event="false"
        ref="vuecal"
        style="width: 100% ;height: 100%;"
    >
    </vue-cal>
  </div>
</template>

<script>
import VueCal from 'vue-cal'
export default {
  components : {
    VueCal,
  },
}
</script>

<style scoped>
.container {
  border-radius: 5px;
  background: #2C2F3B;
  height: 500px;
  padding: 10px;
}
</style>