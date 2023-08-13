import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITeamEvent } from '@/shared/model/team-event.model';
import TeamEventService from './team-event.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TeamEventDetails extends Vue {
  @Inject('teamEventService') private teamEventService: () => TeamEventService;
  @Inject('alertService') private alertService: () => AlertService;

  public teamEvent: ITeamEvent = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.teamEventId) {
        vm.retrieveTeamEvent(to.params.teamEventId);
      }
    });
  }

  public retrieveTeamEvent(teamEventId) {
    this.teamEventService()
      .find(teamEventId)
      .then(res => {
        this.teamEvent = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
