import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PaymentDetailsService } from 'app/entities/payment-details/payment-details.service';
import { IPaymentDetails, PaymentDetails } from 'app/shared/model/payment-details.model';

describe('Service Tests', () => {
  describe('PaymentDetails Service', () => {
    let injector: TestBed;
    let service: PaymentDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentDetails;
    let expectedResult: IPaymentDetails | IPaymentDetails[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentDetailsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PaymentDetails(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PaymentDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PaymentDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PaymentDetails', () => {
        const returnedFromService = Object.assign(
          {
            payoutCurrency: 'BBBBBB',
            paymentMode: 'BBBBBB',
            purposeOfTransfer: 'BBBBBB',
            payOutCountryCode: 'BBBBBB',
            paymentDetails: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PaymentDetails', () => {
        const returnedFromService = Object.assign(
          {
            payoutCurrency: 'BBBBBB',
            paymentMode: 'BBBBBB',
            purposeOfTransfer: 'BBBBBB',
            payOutCountryCode: 'BBBBBB',
            paymentDetails: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PaymentDetails', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
