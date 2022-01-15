<template>
  <div>
    <VideoView/>
    <HowToUse/>
  </div>
</template>

<script>
import HowToUse from '@/components/component/noAccess/Sign/HowToUse.vue';
import VideoView from '@/components/component/noAccess/Sign/VideoView.vue';

export default {
  components:{
    VideoView,
    HowToUse,
  }
};
</script>

<style scoped>
</style>