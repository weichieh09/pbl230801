import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMatchPlayer } from '@/shared/model/match-player.model';
import MatchPlayerService from './match-player.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MatchPlayerDetails extends Vue {
  @Inject('matchPlayerService') private matchPlayerService: () => MatchPlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public matchPlayer: IMatchPlayer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchPlayerId) {
        vm.retrieveMatchPlayer(to.params.matchPlayerId);
      }
    });
  }

  public retrieveMatchPlayer(matchPlayerId) {
    this.matchPlayerService()
      .find(matchPlayerId)
      .then(res => {
        this.matchPlayer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
