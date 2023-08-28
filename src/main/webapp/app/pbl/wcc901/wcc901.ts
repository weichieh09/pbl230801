import axios from 'axios';

const apiBaseUrl = '/api/wcc901';

export default {
  data() {
    return {
      eventId: this.$route.params.eId,
      plyrs: [],
      countJoined: 0,
      page: {
        previousPage: 1,
        currentPage: 1,
        objTotal: 0,
        perPage: 8,
      },
    };
  },
  created() {
    this.getPlyrList();
    this.getCountJoined();
  },
  methods: {
    joinEvent(plyr: any): void {
      axios
        .post(`${apiBaseUrl}/joinEvent`, {
          eId: this.eventId,
          pId: plyr.id,
          joinEv: plyr.joinEv === 'Y' ? 'N' : 'Y',
        })
        .then(response => {
          if (response.data.status === '0') plyr.joinEv = plyr.joinEv === 'Y' ? 'N' : 'Y';
          this.getCountJoined();
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageLoad(page: any): void {
      if (page !== this.page.previousPage) {
        this.page.previousPage = page;
        this.getPlyrList();
      }
    },
    getCountJoined(): void {
      axios
        .get(`${apiBaseUrl}/countJoined`, {
          params: {
            'eId.equals': this.eventId,
          },
        })
        .then(response => {
          this.countJoined = response.data;
        });
    },
    getPlyrList(): void {
      axios
        .get(`${apiBaseUrl}/players`, {
          params: {
            'eId.equals': this.eventId,
            sort: 'plyrLvl,asc',
            page: this.page.currentPage - 1,
            size: this.page.perPage,
          },
        })
        .then(response => {
          if (response.data.length > 0) {
            this.plyrs = response.data;
            this.page.objTotal = Number(response.headers['x-total-count']);
          }
        });
    },
  },
};
