import Vue from 'vue';

export default class LoginService {
  public openLogin(instance: Vue): void {
    this.$router.push('/login');
  }
}
