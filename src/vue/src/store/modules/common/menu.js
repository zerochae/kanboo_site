const menu = {
  namespaced: true,
  state: {
    isOpenNotification: false,
    isOpenUserSetting: false
  },
  mutations: {
    notificationClick(state) {
      if(state.isOpenUserSetting) {
        state.isOpenUserSetting = false
      }
      state.isOpenNotification = !state.isOpenNotification
    },
    userSettingClick(state) {
      if(state.isOpenNotification) {
        state.isOpenNotification = false
      }
      state.isOpenUserSetting = !state.isOpenUserSetting
    }
  },
  actions: {
    
  }
}

export default menu