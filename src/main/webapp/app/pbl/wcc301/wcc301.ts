const apiBaseUrl = '/api/wcc301';
import AccountService from '@/account/account.service';

export default {
  inject: ['accountService'],
  data() {
    return {
      hasAnyAuthorityValues: {},
    };
  },
  methods: {
    authenticated(): boolean {
      return this.$store.getters.authenticated;
    },
    hasAnyAuthority(authorities: any): boolean {
      this.accountService()
        .hasAnyAuthorityAndCheckAuth(authorities)
        .then(value => {
          if (this.hasAnyAuthorityValues[authorities] !== value) {
            this.hasAnyAuthorityValues = { ...this.hasAnyAuthorityValues, [authorities]: value };
          }
        });
      return this.hasAnyAuthorityValues[authorities] ?? false;
    },
    logout(): Promise<any> {
      localStorage.removeItem('jhi-authenticationToken');
      sessionStorage.removeItem('jhi-authenticationToken');
      this.$store.commit('logout');
      if (this.$route.path !== '/') {
        return this.$router.push('/');
      }
      return Promise.resolve(this.$router.currentRoute);
    },
  },
};
