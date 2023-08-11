import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeamPlayer } from '@/shared/model/team-player.model';
import TeamPlayerService from './team-player.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TeamPlayerDetails extends Vue {
  @Inject('teamPlayerService') private teamPlayerService: () => TeamPlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public teamPlayer: ITeamPlayer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamPlayerId) {
        vm.retrieveTeamPlayer(to.params.teamPlayerId);
      }
    });
  }

  public retrieveTeamPlayer(teamPlayerId) {
    this.teamPlayerService()
      .find(teamPlayerId)
      .then(res => {
        this.teamPlayer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
