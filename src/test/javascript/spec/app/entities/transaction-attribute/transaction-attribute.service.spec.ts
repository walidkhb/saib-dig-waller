import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TransactionAttributeService } from 'app/entities/transaction-attribute/transaction-attribute.service';
import { ITransactionAttribute, TransactionAttribute } from 'app/shared/model/transaction-attribute.model';

describe('Service Tests', () => {
  describe('TransactionAttribute Service', () => {
    let injector: TestBed;
    let service: TransactionAttributeService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransactionAttribute;
    let expectedResult: ITransactionAttribute | ITransactionAttribute[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransactionAttributeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TransactionAttribute(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransactionAttribute', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TransactionAttribute()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransactionAttribute', () => {
        const returnedFromService = Object.assign(
          {
            narativeLine1: 'BBBBBB',
            narativeLine2: 'BBBBBB',
            narativeLine3: 'BBBBBB',
            narativeLine4: 'BBBBBB',
            clientRefNumber: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransactionAttribute', () => {
        const returnedFromService = Object.assign(
          {
            narativeLine1: 'BBBBBB',
            narativeLine2: 'BBBBBB',
            narativeLine3: 'BBBBBB',
            narativeLine4: 'BBBBBB',
            clientRefNumber: 'BBBBBB',
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

      it('should delete a TransactionAttribute', () => {
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
