package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(final CategoryRepository categoryRepository, final CustomerRepository customerRepository,
                     final VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        createCategories();
        createCustomers();
        createVendors();
    }

    private void createCategories() {
        final Category fruits = new Category();
        fruits.setName("Fruits");

        final Category dried = new Category();
        dried.setName("Dried");

        final Category fresh = new Category();
        fresh.setName("Fresh");

        final Category exotic = new Category();
        exotic.setName("Exotic");

        final Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded = " + categoryRepository.count());
    }

    private void createCustomers() {
        final Customer custOne = new Customer();
        custOne.setFirstname("Bob");
        custOne.setLastname("Jones");

        final Customer custTwo = new Customer();
        custTwo.setFirstname("John");
        custTwo.setLastname("Smith");

        final Customer custThree = new Customer();
        custThree.setFirstname("Mike");
        custThree.setLastname("Myers");

        final Customer custFour = new Customer();
        custFour.setFirstname("Jason");
        custFour.setLastname("Vorhees");

        final Customer custFive = new Customer();
        custFive.setFirstname("Freddy");
        custFive.setLastname("Krueger");

        customerRepository.save(custOne);
        customerRepository.save(custTwo);
        customerRepository.save(custThree);
        customerRepository.save(custFour);
        customerRepository.save(custFive);

        System.out.println("Customers Loaded = " + customerRepository.count());
    }

    private void createVendors() {
        final Vendor vendorOne = new Vendor();
        vendorOne.setName("Vendor1");

        final Vendor vendorTwo = new Vendor();
        vendorTwo.setName("Vendor2");

        vendorRepository.save(vendorOne);
        vendorRepository.save(vendorTwo);

        System.out.println("Vendors Loaded = " + vendorRepository.count());
    }
}
