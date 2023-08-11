import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPlayer } from '@/shared/model/player.model';
import PlayerService from './player.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PlayerDetails extends Vue {
  @Inject('playerService') private playerService: () => PlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public player: IPlayer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerId) {
        vm.retrievePlayer(to.params.playerId);
      }
    });
  }

  public retrievePlayer(playerId) {
    this.playerService()
      .find(playerId)
      .then(res => {
        this.player = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
