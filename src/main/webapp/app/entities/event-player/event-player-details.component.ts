import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEventPlayer } from '@/shared/model/event-player.model';
import EventPlayerService from './event-player.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EventPlayerDetails extends Vue {
  @Inject('eventPlayerService') private eventPlayerService: () => EventPlayerService;
  @Inject('alertService') private alertService: () => AlertService;

  public eventPlayer: IEventPlayer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.eventPlayerId) {
        vm.retrieveEventPlayer(to.params.eventPlayerId);
      }
    });
  }

  public retrieveEventPlayer(eventPlayerId) {
    this.eventPlayerService()
      .find(eventPlayerId)
      .then(res => {
        this.eventPlayer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
