import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchZ } from '@/shared/model/match-z.model';
import MatchZService from './match-z.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MatchZDetails extends Vue {
  @Inject('matchZService') private matchZService: () => MatchZService;
  @Inject('alertService') private alertService: () => AlertService;

  public matchZ: IMatchZ = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchZId) {
        vm.retrieveMatchZ(to.params.matchZId);
      }
    });
  }

  public retrieveMatchZ(matchZId) {
    this.matchZService()
      .find(matchZId)
      .then(res => {
        this.matchZ = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
