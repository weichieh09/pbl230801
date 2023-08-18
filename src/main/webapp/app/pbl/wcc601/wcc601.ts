import axios from 'axios';

const apiBaseUrl = '/api/wcc601';

export default {
  data() {
    return {
      eventZs: [],
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 5,
      },
    };
  },
  created() {
    this.getEventZList();
  },
  methods: {
    getSimpleTime(str: string): string {
      const date = new Date(str);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      const hour = date.getHours().toString().padStart(2, '0');
      const minute = date.getMinutes().toString().padStart(2, '0');
      return `${year}-${month}-${day} ${hour}:${minute}`;
    },
    test(team: any): void {
      this.$router.push(`/pbl/wcc601/${team.id}/wcc901`);
    },
    prepareRemoveEventZ(eventZ: any): void {
      this.$refs['removeEventZ-modal'].show();
      this.$refs['removeEventZ-modal'].eventZ = eventZ;
    },
    removeEventZ(): void {
      axios
        .delete(`${apiBaseUrl}/event-zs/${this.$refs['removeEventZ-modal'].eventZ.id}`)
        .then(response => {
          if (response.data.status === '0') {
            this.$bvToast.toast('刪除賽事成功', {
              toaster: 'b-toaster-top-center',
              title: '刪除成功',
              variant: 'success',
              solid: true,
            });
            this.$refs['removeEventZ-modal'].hide();
            this.getEventZList();
          } else {
            this.$bvToast.toast('刪除賽事失敗', {
              toaster: 'b-toaster-top-center',
              title: '刪除失敗',
              variant: 'danger',
              solid: true,
            });
          }
        })
        .catch(error => {
          console.log(error);
          this.$bvToast.toast('刪除賽事失敗', {
            toaster: 'b-toaster-top-center',
            title: '刪除失敗',
            variant: 'danger',
            solid: true,
          });
        });
    },
    createEventZ(): void {
      this.$router.push('/pbl/wcc601/0');
    },
    editEventZ(eventZ: any): void {
      this.$router.push(`/pbl/wcc601/${eventZ.id}`);
    },
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getEventZList();
      }
    },
    getEventZList(): void {
      this.eventZs = [];
      axios
        .get(`${apiBaseUrl}/event-zs`, {
          params: {
            sort: 'id,desc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.eventZs = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          }
        });
    },
  },
};
