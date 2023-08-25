import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserTeam } from '@/shared/model/user-team.model';
import UserTeamService from './user-team.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UserTeamDetails extends Vue {
  @Inject('userTeamService') private userTeamService: () => UserTeamService;
  @Inject('alertService') private alertService: () => AlertService;

  public userTeam: IUserTeam = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userTeamId) {
        vm.retrieveUserTeam(to.params.userTeamId);
      }
    });
  }

  public retrieveUserTeam(userTeamId) {
    this.userTeamService()
      .find(userTeamId)
      .then(res => {
        this.userTeam = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
