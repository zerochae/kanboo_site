const sign = {
  namespaced: true,
  state: {
    languages: {
      ko: {
        welcome: [
          "메뉴를 선택해주세요. 1.로그인 2.회원가입 3.찾기 4.clear 5.cd.. 6.cd home 7.help",
          `깐부 터미널에 오신것을 환영합니다. 이 공간에서의 모든 로직은 모두 타이핑으로 이루어집니다.`,
          `다음 메뉴를 원하시는 메뉴를 타이핑해주세요. 로그인을 하시려면 login을 타이핑 하시면 됩니다.`,
          `메뉴 설명`,
          `1. 로그인`,
          `2. 회원가입`,
          `3. 찾기 : 아이디 , 비밀번호 찾기`,
          `4. clear : 모든 내용 지우기`,
          `5. cd .. : 한 단계 뒤로`,
          `6. cd home : 처음으로`,
          `7. en : 언어를 영어로 변경 / ko : 언어를 한국어로 변경`,
          `8. help : 도움메시지 출력하기`,
        ],
        sign: [
          "ID를 입력해주세요. 아이디는 6~20자리의 영어와 숫자만 가능합니다.",
          "비밀번호를 입력해주세요. 비밀번호는 6~20자리의 영어와 숫자가 반드시 필요하고, 대문자영어와 특수문자도 가능합니다.",
          "비밀번호를 다시 한번 입력해주세요.",
          "닉네임을 입력해주세요. 닉네임은 6~20자리의 영어와 숫자만 가능하고, 다른사람과 중복이 가능합니다.",
          "휴대폰 번호는 분실시에 SMS로 임시비밀번호를 발송해드리기 때문에 정확하게 입력해 주세요. 확인하셨으면 [Y]를 입력해주세요.",
          "휴대폰 번호를 입력해주세요. ex +82) 1234-5678 ",
        ],
        login: ["ID를 입력해주세요.", "비밀번호를 입력해주세요"],
        find: ["ID와 PW중 무엇을 찾으시겠습니까?", "토큰 번호를 입력해주세요"],
        etc: [
          "잘못된 토큰 번호 입니다.",
          "임시 비밀번호를 SMS로 전송하였습니다.",
        ],
      },
      en: {
        welcome: [
          "Please select a menu 1.login 2.sign 3.find 4.clear 5.cd.. 6.cd home 7.help",
          `Welcome to Kanboo Terminal. All logic in this space is done by typing.`,
          `Please type in the menu you want the next menu to be. To log in, just type login.`,
          "Menu Description",
          `1. login`,
          `2. sign`,
          `3. find : Find ID and password`,
          `4. clear : clear everything`,
          `5. cd .. : one step back `,
          `6. cd home : first `,
          `7. en : change language to english / ko : change language to korean`,
          `8. help : print help message`,
        ],
        sign: [
          "Enter your ID. ID must be 6 to 20 characters in English and numbers.",
          "Enter your Password A password must be 6 to 20 character with a combination of English and numbers, and uppercase English and special character are optional",
          "Re-enter your Password",
          "Enter your NickName. ID must be 6 to 20 characters in English and numbers. And it can be duplicated with that of other people.",
          "Please enter your mobile phone number correctly as a temporary password will be sent via SMS in case of loss. After confirming, Enter [Y].",
          "Enter your Phone-Number. ex. +82) 1234-5678",
        ],
        login: ["Enter your ID", "Enter your PW"],
        find: ["ID or PW?", "Enter your Token"],
        etc: [
          "You entered an Invalid Token.",
          "A temporary password has been sent via SMS.",
        ],
      },
    },
    signHelp: "",
    loginHelp: "",
    findHelp: "",
    etcHelp: "",
    welcomeHelp: "",
    isFirst: true,
  },
  mutations: {
    isLang(state, payload) {
      if (state.isFirst) {
        state.signHelp = state.languages.en.sign;
        state.loginHelp = state.languages.en.login;
        state.findHelp = state.languages.en.find;
        state.etcHelp = state.languages.en.etc;
        state.welcomeHelp = state.languages.en.welcome;
        state.isFirst = false;
      } else {
        state.signHelp = state.languages[payload].sign;
        state.loginHelp = state.languages[payload].login;
        state.findHelp = state.languages[payload].find;
        state.etcHelp = state.languages[payload].etc;
        state.welcomeHelp = state.languages[payload].welcome;
      }
    },
  },
  actions: {},
};

export default sign;
