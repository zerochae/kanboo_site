<template>
  <div class="modal-container" @click="backgroundClose">
    <div class="modal-box-wrap">

      <div class="top-wrap">
        <div class="title">Create</div>
        <button @click="openAddModal" class="close">X</button>
      </div>

      <div class="add-form">
        <input v-model="requestData.name" type="text" class="name" placeholder="File or Directory Name">

        <select v-model="requestData.type" class="type" v-if="isJava">
          <option v-for="item in type" :key="item">{{item}}</option>
        </select>

        <select v-model="requestData.path" class="path" v-if="isJava">
          <option :value="null">Path</option>
          <option v-for="item in path" :key="item" :value="item">{{item.label}}</option>
        </select>

        <select v-model="requestData.classification" class="classification" v-if="isJava">
          <option v-for="item in classification" :key="item">{{item}}</option>
        </select>
      </div>

      <button class="create-btn" @click="createDirOrFile" type="button">create</button>

    </div>
  </div>
</template>

<script>
import {mapActions, mapMutations, mapState} from "vuex";

export default {
  name: "AddModal",
  computed: {
    ...mapState({
      type: state => state.javaCompile.type,
      path: state => state.javaCompile.path,
      classification: state => state.javaCompile.classification,
      name: state => state.javaCompile.name,
      requestData: state => state.javaCompile.requestData,
      isJava: state => state.javaCompile.isJava
    })
  },
  methods: {
    ...mapMutations({
      openAddModal: "javaCompile/openAddModal"
    }),
    ...mapActions({
      createDirOrFile: "javaCompile/createDirOrFile"
    }),
    backgroundClose(e) {
      if(e.target.classList[0] === 'modal-container') {
        this.openAddModal()
      }
    }
  }
}
</script>

<style scoped>
.modal-container {
  background: #00000050;
  width: 100%;
  height: calc(100vh - 70px);
  position: absolute;
  top: 70px;
  left: 0;
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-box-wrap {
  width: 40vw;
  background: #2C2F3B;
  border-radius: 5px;
  padding: 10px;
}

.top-wrap {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.title {
  font-size: 20px;
}

.close {
  color: #999;
  cursor: pointer;
  transition: color .2s ease-in;
}

.close:hover {
  color: #fff;
}

.add-form {
  display: flex;
  margin-bottom: 10px;
}

.name {
  width: 90%;
  margin-right: 20px;
  outline: none;
  border: none;
  border-bottom: 1px solid #999;
  background: transparent;
  padding: 5px 10px;
  color: #fff;
}

select {
  border: none;
  background: none;
  color: #fff;
  outline: none;
  width: 140px;
  text-align: center;
}

option {
  color: #fff;
}

.create-btn {
  color: #fff;
  cursor: pointer;
  background: #FF8906;
  padding: 5px 10px;
  font-size: 16px;
  border-radius: 5px;
  margin-left: auto;
  display: block;
}
</style>