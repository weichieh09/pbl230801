import LoginService from '@/account/login.service';

export default {
  data() {
    return {
      loginService: new LoginService(),
    };
  },
  methods: {
    openLogin(): void {
      this.loginService.openLogin(this.$root);
    },
  },
};
